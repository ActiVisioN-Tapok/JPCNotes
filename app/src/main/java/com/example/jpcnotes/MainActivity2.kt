package com.example.jpcnotes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jpcnotes.data.model.UserState
import com.example.jpcnotes.data.network.SupabaseClient.client
import com.example.jpcnotes.ui.theme.JPCNotesTheme
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity2: ComponentActivity() {
    private val viewModel = SupabaseAuthViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JPCNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "Home") {
                        composable("home") { HomeScreen() }
                        composable("preps") { PrepsScreen() }
                        composable("profile") { ProfileScreen() }
                    }

                    BNBInit(navController)
                }
            }
        }
    }
}

@Composable
fun BNBInit(navController: NavController)
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        contentAlignment = Alignment.BottomCenter
    )
    {
        val navItems = listOf(
            BottomNavItem.Home,
            BottomNavItem.Preps,
            BottomNavItem.Profile
        )
        var selectedItem by remember {
            mutableStateOf(navItems[0])
        }

        NavigationBar {
            navItems.forEach{
                item ->
                NavigationBarItem(selected = selectedItem == item,
                    onClick = {
                        selectedItem = item
                        navController.navigate(item.route)
                              },
                    icon = { Icon(
                        item.icon,
                    contentDescription = null
                )
                           },
                    label = {
                        Text(
                            text = item.label
                        )
                    }
                )
            }

        }
    }
}

@Composable
fun HomeScreen() {
    val items =
    LazyColumn {
        items()
    }
}

@Composable
fun LcElement()
{

}

@Composable
fun PrepsScreen() {
    Text(text = "Препараты")
    Log.e("Out", "Home")
    }


@Composable
fun ProfileScreen() {
    Text(text = "Профиль")
    Log.e("Out", "Profile")
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val selectedIcon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Filled.Home, Icons.Filled.Home, "Главная")
    object Preps : BottomNavItem("preps", Icons.Filled.Person, Icons.Filled.List, "Препараты")
    object Profile : BottomNavItem("profile", Icons.Filled.AccountBox, Icons.Filled.AccountBox, "Профиль")
}