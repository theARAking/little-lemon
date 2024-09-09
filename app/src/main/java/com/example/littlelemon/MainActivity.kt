package com.example.littlelemon

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private val sharedPreferences by lazy {
        getSharedPreferences("LittleLemon", MODE_PRIVATE)
    }

    private val firstNameLiveData = MutableLiveData<String>()
    private val lastNameLiveData = MutableLiveData<String>()
    private val emailLiveData = MutableLiveData<String>()

    private val sharedPreferencesListener = OnSharedPreferenceChangeListener { sharedPreferences, key ->
        when (key) {
            "FirstName" -> firstNameLiveData.value = sharedPreferences.getString(key, "")
            "LastName" -> lastNameLiveData.value = sharedPreferences.getString(key, "")
            "Email" -> emailLiveData.value = sharedPreferences.getString(key, "")
            else -> {}
        }
    }

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            MenuDatabase::class.java,
            "menu.db"
        ).build()
    }

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    suspend fun updateMenuDatabase() {
        val response: MenuNetwork = client
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body()

        database.menuDao().clearMenuItems()

        response.menu.forEach {
            database.menuDao().saveMenuItem(it.toMenuItem())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firstNameLiveData.value = sharedPreferences.getString("FirstName", "")
        lastNameLiveData.value = sharedPreferences.getString("LastName", "")
        emailLiveData.value = sharedPreferences.getString("Email", "")

        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)

        enableEdgeToEdge()
        setContent {
            LittleLemonTheme {
                val firstName = firstNameLiveData.observeAsState("")
                val lastName = lastNameLiveData.observeAsState("")
                val email = emailLiveData.observeAsState("")

                val onUserDataChange = {newFirstName: String, newLastName: String, newEmail: String ->
                    sharedPreferences.edit(commit = true) {
                        putString("FirstName", newFirstName)
                        putString("LastName", newLastName)
                        putString("Email", newEmail)
                    }
                }

                val navController = rememberNavController()

                val updateMenuDatabase = {
                    lifecycleScope.launch {
                        withContext(IO) {
                            updateMenuDatabase()
                        }
                    }
                }

                Navigation(
                    navController = navController,
                    sharedPreferences = sharedPreferences,
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    onUserDataChange = onUserDataChange,
                    database = database,
                    updateMenuDatabase = updateMenuDatabase
                )
            }
        }
    }
}