package com.example.jpcnotes

import android.content.Intent

import android.os.Bundle

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.jpcnotes.data.model.UserState
//import com.example.jpcnotes.data.network.SupabaseClient.client

import com.example.jpcnotes.ui.theme.JPCNotesTheme

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jpcnotes.data.network.User

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JPCNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }


    @Composable
    fun MainScreen(
        viewModel: SupabaseAuthViewModel = viewModel()
    ) {
        val context = LocalContext.current
        val userState by viewModel.userState

        var userEmail by remember { mutableStateOf("") }
        var userPassword by remember { mutableStateOf("") }

        var currentUserState by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            viewModel.isUserLoggedIn(
                context,
            )
        }

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
                viewModel.signUp(
                    context,
                    userEmail,
                    userPassword,
                )
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color(198, 124, 98)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(383.dp)) {
                Text(text = "Sign Up", color = Color(255, 255, 255))
            }

            Button(onClick = {
                viewModel.login(
                        context,
                        userEmail,
                        userPassword,
                    )
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
                    viewModel.logout(context)
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(383.dp)
            )
            {
                Text(text = "Logout", color = Color(255, 255, 255))
            }

            when (userState) {

                is UserState.Loading -> {
                    LoadingComponent()
                }

                is UserState.Success -> {
                    val message = (userState as UserState.Success).message
                    currentUserState = message
                    

                    if(message == "User already logged in!" || message == "Logged in successfully!")
                    {
                        val win2 = Intent(this@MainActivity, MainActivity2::class.java)
                        startActivity(win2)
                    }
                }

                is UserState.Error -> {
                    val message = (userState as UserState.Error).message
                    currentUserState = message
                }

                else -> {}
            }

            if (currentUserState.isNotEmpty()) {
                Text(text = currentUserState)
            }
        }
    }
}
