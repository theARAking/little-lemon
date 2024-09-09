package com.example.littlelemon

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.LLPrimary1
import com.example.littlelemon.ui.theme.LLSecondary3
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun Onboarding(
    navController: NavHostController?,
    firstName: State<String>?,
    lastName: State<String>?,
    email: State<String>?,
    onUserDataChange: ((String, String, String) -> Unit)?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    var firstNameField by remember {
        mutableStateOf(firstName?.value ?: "")
    }
    var lastNameField by remember {
        mutableStateOf(lastName?.value ?: "")
    }
    var emailField by remember {
        mutableStateOf(email?.value ?: "")
    }

    Scaffold(
        topBar = {TopBar(includeProfile = false)},
        bottomBar = {
            NavigationBar() {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LittleLemonButton(
                        buttonText = "Register",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .align(Alignment.Center)
                    ) {
                        val firstNameValue = firstNameField.toString()
                        val lastNameValue = lastNameField.toString()
                        val emailValue = emailField.toString()

                        if (firstNameValue.isBlank() || lastNameValue.isBlank() || emailValue.isBlank()) {
                            Toast.makeText(context, "Registration unsuccessful. Please enter all data.", Toast.LENGTH_SHORT).show()
                        }
                        else {
                            onUserDataChange?.invoke(firstNameValue, lastNameValue, emailValue)

                            Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                            navController?.navigate(Home.route)
                        }
                    }
                }
            }
        },
        modifier = modifier.fillMaxSize()
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
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = LLPrimary1)
                ) {
                    Text(
                        text = "Let's get to know you",
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center,
                        color = LLSecondary3,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(20.dp)
                    )
                }

                Text(
                    text = "Personal Information",
                    style = MaterialTheme.typography.headlineMedium,
                    color = LLPrimary1,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(15.dp)
                )

                TextField(
                    label = {
                        Text(
                            text = "First Name",
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    value = firstNameField,
                    onValueChange = { firstNameField = it },
                    modifier = Modifier.padding(10.dp)
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
                    modifier = Modifier.padding(10.dp)
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    LittleLemonTheme {
        Onboarding(
            navController = null,
            firstName = null,
            lastName = null,
            email = null,
            onUserDataChange = null
        )
    }
}