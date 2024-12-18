package com.example.libraryapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.libraryapp.presentation.screens.bookdetail.BookDetailScreen
//import com.example.libraryapp.presentation.screens.bookdetail.BookDetailScreen
import com.example.libraryapp.presentation.screens.booklist.BookListScreen
import com.example.libraryapp.presentation.screens.booklist.BookListViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "bookList"
    ) {
        composable("bookList") {
            BookListScreen(
                navigateToDetail = { bookId ->
                    navController.navigate("bookDetail/$bookId")
                }
            )
        }

        composable(
            route = "bookDetail/{bookId}",
            arguments = listOf(navArgument("bookId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: return@composable
            BookDetailScreen(
                bookId = bookId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

//@Composable
//fun NavigationGraph(
//    navController: NavHostController = rememberNavController()
//) {
//    NavHost(
//        navController = navController,
//        startDestination = "bookList"
//    ) {
//        composable("bookList") {
//            BookListScreen(
//                navigateToDetail = { bookId ->
//                    navController.navigate("bookDetail/$bookId")
//                }
//            )
//        }
//
//        composable(
//            route = "bookDetail/{bookId}",
//            arguments = listOf(navElement("bookId"))
//        ) { backStackEntry ->
//            val bookId = backStackEntry.arguments?.getString("bookId") ?: return@composable
//            BookDetailScreen(
//                bookId = bookId,
//                onBackClick = { navController.popBackStack() }
//            )
//        }
//    }
//}