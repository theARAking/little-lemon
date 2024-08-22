package com.example.littlelemon

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LLPrimary1
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Profile(navController: NavHostController?, sharedPreferences: SharedPreferences?, firstName: State<String>?, lastName: State<String>?, email: State<String>?, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Scaffold(
        topBar = {TopBar(includeProfile = false)},
        bottomBar = {
            NavigationBar() {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LittleLemonButton(
                        buttonText = "Log out",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .align(Alignment.Center)
                    ) {
                        sharedPreferences?.edit()?.clear()?.commit()

                        Toast.makeText(context, "Successfully logged out.", Toast.LENGTH_SHORT).show()
                        navController?.navigate(Onboarding.route)
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Personal Information",
                    style = MaterialTheme.typography.headlineMedium,
                    color = LLPrimary1,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(15.dp)
                )

                Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Text(
                        text = "First Name:",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = firstName?.value ?: "",
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Text(
                        text = "Last Name:",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = lastName?.value ?: "",
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                Column(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Text(
                        text = "Email:",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = email?.value ?: "",
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                /*
                TextField(
                    label = {
                        Text(
                            text = "First Name",
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    value = firstNameField,
                    onValueChange = { firstNameField = it },
                    modifier = Modifier.padding(10.dp),
                    enabled = false
                )

                TextField(
                    label = {
                        Text(
                            text = "Last Name",
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    value = lastNameField,
                    onValueChange = { lastNameField = it },
                    modifier = Modifier.padding(10.dp),
                    enabled = false
                )

                TextField(
                    label = {
                        Text(
                            text = "Email",
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    value = emailField,
                    onValueChange = { emailField = it },
                    modifier = Modifier.padding(10.dp),
                    enabled = false
                )
                 */
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    LittleLemonTheme {
        Profile(null, null, null, null, null)
    }
}