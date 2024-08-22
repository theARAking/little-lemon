package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController, sharedPreferences: SharedPreferences, firstName: State<String>, lastName: State<String>, email: State<String>, onUserDataChange: (String, String, String) -> Unit) {
    val hasUserData = (firstName.value.isNotBlank() || lastName.value.isNotBlank() || email.value.isNotBlank())
    val startDestination = if (hasUserData) Onboarding.route else Home.route
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) {
            Onboarding(navController, firstName, lastName, email, onUserDataChange)
        }
        composable(Home.route) {
            Home(navController)
        }
        composable(Profile.route) {
            Profile(navController, sharedPreferences, firstName, lastName, email)
        }
    }
}