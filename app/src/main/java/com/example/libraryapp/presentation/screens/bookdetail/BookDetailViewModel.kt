package com.example.libraryapp.presentation.screens.bookdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.model.ReadStatus
import com.example.libraryapp.domain.usecase.GetBookByIdUseCase
import com.example.libraryapp.domain.usecase.UpdateBookStatusUseCase
import com.example.libraryapp.presentation.model.BookDetailUiState
import com.example.libraryapp.presentation.model.BookUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val getBookByIdUseCase: GetBookByIdUseCase,
    private val updateBookStatusUseCase: UpdateBookStatusUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookDetailUiState>(BookDetailUiState.Loading)
    val uiState: StateFlow<BookDetailUiState> = _uiState.asStateFlow()

    fun loadBook(id: String) {
        viewModelScope.launch {
            _uiState.value = BookDetailUiState.Loading
            try {
                val book = getBookByIdUseCase(id)
                book?.let {
                    _uiState.value = BookDetailUiState.Success(BookUI.fromDomain(it))
                } ?: run {
                    _uiState.value = BookDetailUiState.Error("Libro no encontrado")
                }
            } catch (e: Exception) {
                _uiState.value = BookDetailUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun updateReadStatus(newStatus: ReadStatus) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is BookDetailUiState.Success) {
                try {
                    updateBookStatusUseCase(currentState.book.id, newStatus)
                    loadBook(currentState.book.id)
                } catch (e: Exception) {
                    _uiState.value = BookDetailUiState.Error("Error al actualizar estado")
                }
            }
        }
    }
}