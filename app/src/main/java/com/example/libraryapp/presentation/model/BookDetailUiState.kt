package com.example.libraryapp.presentation.model

sealed class BookDetailUiState {
    data object Loading : BookDetailUiState()
    data class Success(val book: BookUI) : BookDetailUiState()
    data class Error(val message: String) : BookDetailUiState()
}