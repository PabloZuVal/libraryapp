package com.example.libraryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.libraryapp.presentation.navigation.NavigationGraph
import com.example.libraryapp.ui.theme.LibraryappTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibraryappTheme {
                LibraryApp()
            }
        }
    }
}

@Composable
fun LibraryApp() {
    val navController = rememberNavController()
    NavigationGraph(navController = navController)
}