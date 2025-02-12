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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
        modifier = Modifier.fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.BottomCenter
    )
    {
        NavigationBar {
            NavigationBarItem(selected = false,
                onClick = {
                    navController.navigate("home")
                },
                icon = {
                    Icon(Icons.Filled.Home,
                        contentDescription = "Home")
                },
                label = { Text(text = "Главная")}
            )

            NavigationBarItem(selected = false,
                onClick = {
                    navController.navigate("preps")
                },
                icon = {
                    Icon(Icons.Filled.List,
                        contentDescription = "Preps")
                },
                label = { Text(text = "Препараты")}
            )

            NavigationBarItem(selected = false,
                onClick = {
                    navController.navigate("profile")
                },
                icon = {
                    Icon(Icons.Filled.AccountBox,
                        contentDescription = "Profile")
                },
                label = { Text(text = "Профиль")}
            )

        }
    }
}

@Composable
fun HomeScreen() {
    Text(text = "Домашний экран")
    Log.e("Out", "Home")
}

@Composable
fun PrepsScreen() {
        val context = LocalContext.current


        var userEmail by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }

        var currentUserState by remember { mutableStateOf("") }



        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text = "Email Address")
            }

            Row {
                OutlinedTextField(value = userEmail,
                    onValueChange = { userEmail = it },
                    placeholder = { Text("john@example.com") },
                    leadingIcon = {
                        Icon(
                            Icons.Filled.Email,
                            contentDescription = "Email address"
                        )
                    },
                    modifier = Modifier.width(383.dp))
            }
            Row {
                Text(text = "Password")
            }

            Row {
                OutlinedTextField(value = userPassword,
                    onValueChange = {userPassword = it},
                    placeholder = { Text("Password123")},
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.width(383.dp))
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(onClick = {

            },
                colors = ButtonDefaults.buttonColors(containerColor = Color(198, 124, 98)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(383.dp)) {
                Text(text = "Sign Up", color = Color(255, 255, 255))
            }

            Button(onClick = {

            },
                colors = ButtonDefaults.buttonColors(containerColor = Color(198, 124, 98)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(383.dp))
            {
                Text(text = "Login", color = Color(255, 255, 255))
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                onClick = {

                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(383.dp)
            )
            {
                Text(text = "Logout", color = Color(255, 255, 255))
            }
        }
    }


@Composable
fun ProfileScreen() {
    Text(text = "Профиль")
    Log.e("Out", "Profile")
}