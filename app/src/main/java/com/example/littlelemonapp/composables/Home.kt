package com.example.littlelemonapp.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemonapp.R
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemonapp.MenuItemRoom
import com.example.littlelemonapp.ui.theme.PrimaryGreen
import com.example.littlelemonapp.ui.theme.PrimaryYellow



@Composable
fun Home(navController: NavHostController) {
    Column {
        TopAppBar(navController = navController )
        UpperPanel()
    }
}
@Composable
fun TopAppBar(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .size(30.dp)

        )
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier
                .fillMaxWidth()
                .size(30.dp)
                .padding(end = 20.dp)
                .clickable { navController.navigate("Profile") }

        )
    }
}
@Composable
fun UpperPanel(){

    val restaurantName = "Little Lemon"
    val city = "Chicago"
    val description = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist"


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .background(PrimaryGreen)
                .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 16.dp)
        ) {
            Text(
                text = restaurantName,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryYellow
            )
            Text(
                text = city,
                fontSize = 24.sp,
                color = Color(0xFFEDEFEE)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFEDEFEE),
                    modifier = Modifier
                        .padding(bottom = 28.dp, end = 20.dp)
                        .fillMaxWidth(0.6f)
                )
                Image(
                    painter = painterResource(id = R.drawable.hero_image),
                    contentDescription = "Upper Panel Image",
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                )
            }
            Button(
                onClick = { }
            ) {
                Text(
                    text = "Search"
                )
            }
        }
    }
}

@Composable
fun MenuItems(items: List<MenuItemRoom>) {
    LazyColumn {
        items(items) { menuItem ->
            MenuItem(item = menuItem)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: MenuItemRoom) {
    Column {
        Text(text = item.title, style = MaterialTheme.typography.headlineMedium)
        Text(text = item.description, style = MaterialTheme.typography.bodyLarge)
        Text(text = item.price, style = MaterialTheme.typography.bodyLarge)
        // Use GlideImage to load image from URL
        GlideImage(
            model = item.image,
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home(navController = rememberNavController())
}