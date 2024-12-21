// presentation/navigation/NavigationGraph.kt
package com.example.libraryapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.libraryapp.presentation.screens.booklist.BookListScreen
import com.example.libraryapp.presentation.screens.bookform.BookFormScreen

sealed class Screen(val route: String) {
    object BookList : Screen("bookList")
    object BookForm : Screen("bookForm?bookId={bookId}") {
        fun createRoute(bookId: String? = null) =
            "bookForm" + (bookId?.let { "?bookId=$it" } ?: "")
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.BookList.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.BookList.route) {
            BookListScreen(
                onNavigateToDetail = { bookId ->
                    navController.navigate(Screen.BookForm.createRoute(bookId))
                },
                onNavigateToAdd = {
                    navController.navigate(Screen.BookForm.createRoute())
                }
            )
        }

        composable(
            route = Screen.BookForm.route,
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")
            BookFormScreen(
                bookId = bookId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

