package com.example.littlelemonapp.composables


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemonapp.navigation.Home
import com.example.littlelemonapp.navigation.Onboarding
import com.example.littlelemonapp.navigation.Profile


@Composable
fun NavigationComposable(navController: NavHostController, context: Context) {
    val startDestination = if (isUserDataStored(navController.context)) Home.route else Onboarding.route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Onboarding.route) {
            Onboarding(navController = navController)
        }
        composable(Home.route) {
            Home(navController = navController, context = context)
        }
        composable(Profile.route) {
            Profile(navController = navController)
        }

    }
}

fun isUserDataStored(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString("firstName", null)
    val lastName = sharedPreferences.getString("lastName", null)
    val email = sharedPreferences.getString("email", null)

    return firstName != null && lastName != null && email != null
}