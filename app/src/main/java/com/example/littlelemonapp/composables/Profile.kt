package com.example.littlelemonapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemonapp.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.littlelemonapp.ui.theme.PrimaryGreen
import com.example.littlelemonapp.ui.theme.PrimaryYellow


@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("UserData", Context.MODE_PRIVATE) }

    val firstName = sharedPreferences.getString("firstName", "") ?: ""
    val lastName = sharedPreferences.getString("lastName", "") ?: ""
    val email = sharedPreferences.getString("email", "") ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(80.dp)
                    .padding(20.dp)
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 40.dp, bottom = 100.dp)
            .height(100.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(text = "Personal information:",
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 10.dp),
                style = MaterialTheme.typography.headlineSmall)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

            ) {
            Text(
                text = "First Name: $firstName",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(1.dp, PrimaryGreen, RoundedCornerShape(5.dp))
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Last Name: $lastName",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(1.dp, PrimaryGreen, RoundedCornerShape(5.dp))
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Email: $email",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(1.dp, PrimaryGreen, RoundedCornerShape(5.dp))
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    // Clear SharedPreferences
                    sharedPreferences.edit().clear().apply()

                    // Navigate to the Onboarding screen
                    navController.navigate("Onboarding")
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    PrimaryYellow
                )
            ) {
                Text(text = "Log out",
                    color = Color.Black
                )
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    Profile(navController = rememberNavController())
}