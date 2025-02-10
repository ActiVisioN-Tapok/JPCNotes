package com.example.jpcnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                    NotesList(viewModel)
                }
            }
        }
    }

    @Composable
    fun BottomNavigationExample() {
        val navController = rememberNavController()
        val bottomNavController = rememberSaveable { navController }

        Scaffold(
            bottomBar = {
                BottomNavigationBar(bottomNavController)
            }
        ) { innerPadding ->
            NavHost(
                navController = bottomNavController,
                startDestination = "home",
                Modifier.padding(innerPadding)
            ) {
                composable("home") { HomeScreen() }
                composable("favorites") { FavoritesScreen() }
                composable("profile") { ProfileScreen() }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        NavigationBar {
            NavigationBarItem(
                label = { Text("Home") },
                icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = null) },
                selected = false,  // Здесь можно использовать состояние для выбора
                onClick = {
                    navController.navigate("home") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            NavigationBarItem(
                label = { Text("Favorites") },
                icon = { Icon(imageVector = Icons.Filled.Favorite, contentDescription = null) },
                selected = false,
                onClick = {
                    navController.navigate("favorites") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            NavigationBarItem(
                label = { Text("Profile") },
                icon = { Icon(imageVector = Icons.Filled.Person, contentDescription = null) },
                selected = false,
                onClick = {
                    navController.navigate("profile") {
                        popUpTo("home") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
    @Composable
    fun NotesList(viewModel: SupabaseAuthViewModel) {
        val notes = remember { mutableStateListOf<MainActivity.Note>() }
        LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                val results = client.from("notes").select().decodeList<MainActivity.Note>()
                notes.addAll(results)
            }
        }
        Column {
            Row {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    onClick = {
                        viewModel.logout(this@MainActivity2)
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.width(383.dp)
                )
                {
                    Text(text = "Logout", color = Color(255, 255, 255))
                }
            }
            BottomNavigationExample()
            /*
            LazyColumn {
                items(notes)
                { note ->
                    ListItem(headlineContent = {
                        Text(
                            text = note.body,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    })
                }
            }



            var newNote by remember { mutableStateOf("") }
            val composableScope = rememberCoroutineScope()
            Row {
                OutlinedTextField(value = newNote, onValueChange = {newNote = it})
                Button(onClick = {
                    composableScope.launch(Dispatchers.IO) {
                        val note = client.from("notes").insert(mapOf("body" to newNote))
                        {
                            select()
                            single()
                        }
                            .decodeAs<MainActivity.Note>()
                        notes.add(note)
                        newNote =""
                    }
                }) {
                    Text(text = "Save")
                }
            }

            Row {
                Button(onClick = {
                    val win3 = Intent(this@MainActivity2, MainActivity3::class.java)
                    startActivity(win3)
                }) {
                    Text(text = "Images")
                }
            }
*/

        }

    }
}


@Composable
fun HomeScreen() {
    // Контент для экрана 'Home'
    // Например:
    Text(text = "Home Screen", modifier = Modifier.fillMaxSize())
}

@Composable
fun FavoritesScreen() {
    // Контент для экрана 'Favorites'
    Text(text = "Favorites Screen", modifier = Modifier.fillMaxSize())
}

@Composable
fun ProfileScreen() {
    // Контент для экрана 'Profile'
    Text(text = "Profile Screen", modifier = Modifier.fillMaxSize())
}


@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    JPCNotesTheme {
        Greeting2("Android")
    }
}