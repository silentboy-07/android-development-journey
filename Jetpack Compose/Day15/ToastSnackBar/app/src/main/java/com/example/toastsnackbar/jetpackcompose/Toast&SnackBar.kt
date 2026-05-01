package com.example.toastsnackbar.jetpackcompose

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.time.Duration


@Composable
fun ToastExample () {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally,
    ) {

        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            onClick = {
                Toast.makeText(
                    context,
                    "This is a Toast Message",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
            Text(text = "Show Toast")
        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SnackBarExample() {
    // SnackbarHostState control snackbar
    val snackBarHostState = remember { SnackbarHostState() }

    // Coroutines Scope = To show Snackbar
    val scope = rememberCoroutineScope ()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) {innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(24.dp)
                .padding(innerPadding),
            Arrangement.Center,
            Alignment.CenterHorizontally,
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    scope.launch {
                        // show snackbar here
                        snackBarHostState.showSnackbar(
                            message = "This is a SnackBar",
                            actionLabel = "UNDO",
                            duration = SnackbarDuration.Short
                        )
                    }
                }) {
                Text(text = "Show SnackBar")
            }

        }
    }
    
}