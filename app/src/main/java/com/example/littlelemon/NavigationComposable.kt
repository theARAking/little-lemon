package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.Job

@Composable
fun Navigation(
    navController: NavHostController,
    sharedPreferences: SharedPreferences,
    firstName: State<String>,
    lastName: State<String>,
    email: State<String>,
    onUserDataChange: (String, String, String) -> Unit,
    database: MenuDatabase,
    updateMenuDatabase: () -> Job
) {
    val hasUserData = (firstName.value.isNotBlank() || lastName.value.isNotBlank() || email.value.isNotBlank())
    val startDestination = if (hasUserData) Home.route else Onboarding.route
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) {
            Onboarding(
                navController = navController,
                firstName = firstName,
                lastName = lastName,
                email = email,
                onUserDataChange = onUserDataChange
            )
        }
        composable(Home.route) {
            Home(
                navController = navController,
                database = database,
                updateMenuDatabase = updateMenuDatabase
            )
        }
        composable(Profile.route) {
            Profile(
                navController = navController,
                sharedPreferences = sharedPreferences,
                firstName = firstName,
                lastName = lastName,
                email = email
            )
        }
    }
}