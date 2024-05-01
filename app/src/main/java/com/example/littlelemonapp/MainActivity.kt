package com.example.littlelemonapp


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.littlelemonapp.ui.theme.LittleLemonAppTheme
import androidx.navigation.compose.rememberNavController
import com.example.littlelemonapp.composables.NavigationComposable
import com.example.littlelemonapp.composables.Onboarding
import com.example.littlelemonapp.composables.Home

class MainActivity : ComponentActivity() {

    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
            val navController = rememberNavController()

            LittleLemonAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyNavigation()
                }
            }
        }
    }
}

@Composable
fun MyNavigation() {
    NavigationComposable()
}
