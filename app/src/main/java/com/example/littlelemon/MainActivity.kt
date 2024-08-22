package com.example.littlelemon

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.LittleLemonTheme

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

                Navigation(navController, sharedPreferences, firstName, lastName, email, onUserDataChange)
            }
        }
    }
}