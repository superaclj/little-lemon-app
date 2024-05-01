package com.example.littlelemonapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.background
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemonapp.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.littlelemonapp.ui.theme.PrimaryGreen
import com.example.littlelemonapp.ui.theme.PrimaryYellow


@Composable
fun Onboarding(navController: NavHostController) {
    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var registrationMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(80.dp)
                    .padding(20.dp))
        }
        Row(modifier = Modifier
            .background(color = PrimaryGreen)
            .fillMaxWidth()
            .height(100.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Let's get to know you",
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 40.dp),
            horizontalArrangement = Arrangement.Center
            ) {
            Text(text = "Personal Information",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineSmall)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = registrationMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    if (validateInput(firstName, lastName, email)) {
                        saveUserData(context, firstName, lastName, email)
                        registrationMessage = "Registration successful!"
                        navController.navigate("Home")
                    } else {
                        registrationMessage = "Registration unsuccessful. Please enter all data."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    PrimaryYellow
                )

            ) {
                Text(text = "Register",
                    color = Color.Black)
            }
        }
    }
}

fun validateInput(firstName: String, lastName: String, email: String): Boolean {
    return firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()
}

fun saveUserData(context: Context, firstName: String, lastName: String, email: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    sharedPreferences.edit().apply {
        putString("firstName", firstName)
        putString("lastName", lastName)
        putString("email", email)
        apply()
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    Onboarding(navController = rememberNavController())
}