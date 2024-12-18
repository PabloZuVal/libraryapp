package com.example.libraryapp.presentation.model

sealed class BookListUiState {
    data object Loading : BookListUiState()
    data class Success(val books: List<BookUI>) : BookListUiState()
    data class Error(val message: String) : BookListUiState()
}