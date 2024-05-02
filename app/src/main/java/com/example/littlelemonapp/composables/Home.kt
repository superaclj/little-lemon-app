package com.example.littlelemonapp.composables

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemonapp.AppDatabase
import com.example.littlelemonapp.MenuItemNetwork
import com.example.littlelemonapp.MenuItemRoom
import com.example.littlelemonapp.MenuNetwork
import com.example.littlelemonapp.ui.theme.PrimaryGreen
import com.example.littlelemonapp.ui.theme.PrimaryYellow
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json


@Composable
fun Home(navController: NavHostController, context: Context) {
    Column {
        TopAppBar(navController = navController )
        UpperPanel(context = context)
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
                .padding(horizontal = 10.dp)
                .size(50.dp)
                .weight(1f)
                .align(Alignment.CenterVertically)

        )
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile",
            modifier = Modifier
                .padding(10.dp)
                .size(40.dp)
                .clickable { navController.navigate("Profile") }

        )
    }
}
@Composable
fun UpperPanel(context: Context){

    val database by lazy {
        Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }

    val databaseMenuItems = database.menuItemDao().getAll().observeAsState(emptyList())
    var menuItems by remember { mutableStateOf(databaseMenuItems.value) }

    LaunchedEffect(Unit) {
        if (database.menuItemDao().isEmpty()) {
            val httpClient = HttpClient(Android) {
                install(ContentNegotiation) {
                    json(contentType = ContentType("text", "plain"))
                }
            }
            val menuItemsNetwork = fetchMenu(httpClient)
            saveMenuToDatabase(database, menuItemsNetwork)
        }
    }


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
                .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 1.dp)
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

            var searchPhrase by remember {
                mutableStateOf("")
            }

            TextField(
                value = searchPhrase,
                onValueChange = { searchPhrase = it },
                placeholder = { Text("Enter search phrase",color= Color.Black)},
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .background(Color.White)
            )
            if (searchPhrase.isNotBlank()) {
                menuItems = databaseMenuItems.value.filter { menuItem ->
                    menuItem.title.contains(searchPhrase, true)
                }
            }
        }

    }
    MenuItemsList(items = menuItems)
}
private suspend fun fetchMenu(httpClient: HttpClient): List<MenuItemNetwork> {
    val response: MenuNetwork =
        httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/littleLemonSimpleMenu.json")
            .body()

    return response.menu
}

private fun saveMenuToDatabase(database: AppDatabase, menuItemsNetwork: List<MenuItemNetwork>) {
    val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
    database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun  MenuItemsList(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 20.dp)
    ) {
        items(items = items) { menuItem ->
            Divider(color = Color.Gray, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(text = menuItem.title, fontWeight = FontWeight.Bold)
                    Text(text = "$${menuItem.price}")
                    Text(text = menuItem.description)
                }
                Column {
                    GlideImage(
                        model = menuItem.image,
                        contentDescription = "meal_image",
                        modifier = Modifier.size(100.dp, 100.dp),
                        contentScale = ContentScale.Crop,
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    val context = LocalContext.current
    Home(navController = rememberNavController(), context = context)
}