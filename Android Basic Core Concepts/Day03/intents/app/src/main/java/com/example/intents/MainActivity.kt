package com.example.intents

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.intents.ui.theme.IntentsTheme
import org.jetbrains.annotations.Async

class MainActivity : ComponentActivity() {

    private val viewModel: ImageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentsTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = viewModel.uri,
                        contentDescription = null
                    )
                    Text(text = "Main Activity")
                    Button(onClick = {
                        val intent = Intent(
                            this@MainActivity,
                            SecondActivity::class.java)
                        startActivity(intent)
                    }) {
                        Text(text = "Go to Second Activity")
                    }

                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("https://github.com/silentboy-07")
                        startActivity(intent)
                    }) {
                        Text("Github Link")
                    }

                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.setPackage("com.google.android.apps.maps")
                        try {
                            startActivity(intent)
                        } catch (e: Exception){
                            e.printStackTrace()
                        }

                    }) {
                        Text("Open Maps App")
                    }

                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT,"Hi! I am Vikas")
                        startActivity(Intent.createChooser(intent, "Share via"))
                    }) {
                        Text(text = "Send a message!")
                    }
                }

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val uri = intent.getParcelableExtra(
            Intent.EXTRA_STREAM, Uri::class.java)

        viewModel.updateUri(uri)
    }

}

