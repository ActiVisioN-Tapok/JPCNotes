package com.example.jpcnotes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
//import com.example.jpcnotes.data.network.SupabaseClient
//import com.example.jpcnotes.data.network.SupabaseClient.client
import com.example.jpcnotes.ui.theme.JPCNotesTheme
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.minutes

class MainActivity3 : ComponentActivity() {
    private lateinit var fileUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JPCNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ImageLoad()
                }
            }
        }
    }



    @Composable
    fun ImageLoad()
    {
        val imgs = remember { mutableStateListOf<ImgSpb>() }
        val composableScope = rememberCoroutineScope()
        val client = createSupabaseClient(
            supabaseUrl = "https://nxwxjcymbiblgnglnfwy.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im54d3hqY3ltYmlibGduZ2xuZnd5Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzY3NDE4MzcsImV4cCI6MjA1MjMxNzgzN30.Kt9edX5rJXn59z9xPacukrX_G6LX2D1f05QdJD99UW0")
        {
            install(Postgrest)

        }
        val bucket = client.storage.from("imgs")


        LaunchedEffect(Unit) {

            withContext(Dispatchers.IO) {
                val files = bucket.list()
                files.forEach {
                    file ->
                    val url = bucket.createSignedUrl(file.name, expiresIn = 3.minutes)
                    var img1 = ImgSpb(url)
                    imgs.add(img1)
                }
            }
        }

        LazyColumn {
            composableScope.launch {
                val files = bucket.list()
                items(imgs)
                { img2 ->
                    ListItem(headlineContent = {
                        Image(painter = rememberAsyncImagePainter(img2.path), contentDescription = null, modifier = Modifier.height(100.dp).fillMaxWidth(), contentScale = ContentScale.Crop)
                    })
                }
            }

        }

        Column {

            Row {
                Button(onClick = {
                    val intent = Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT)

                    startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)





                    composableScope.launch {
                        val files = bucket.list()
                        bucket.upload("img${files.size + 1}.png", getImageByteArray(fileUri))
                    }



                }) {
                    Text(text = "Upload image")
                }
            }
        }
    }

    fun getImageByteArray(imageUri: Uri): ByteArray {
        val inputStream = contentResolver.openInputStream(imageUri)
        return inputStream?.readBytes() ?: ByteArray(0)
    }

    @Override
    private fun startActivityForResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            val selectedFile = data?.data // The URI with the location of the file
            if (selectedFile != null) {
                fileUri = selectedFile
            }
        }
    }
}

@Serializable
data class ImgSpb(
    val path: String = ""
)



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JPCNotesTheme {
        Greeting("Android")
    }
}