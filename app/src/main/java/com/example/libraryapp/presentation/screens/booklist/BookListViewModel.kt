package com.example.libraryapp.presentation.screens.booklist
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.model.ReadStatus
import com.example.libraryapp.domain.usecase.GetAllBooksUseCase
import com.example.libraryapp.domain.usecase.UpdateBookStatusUseCase
import com.example.libraryapp.presentation.model.BookListUiState
import com.example.libraryapp.presentation.model.BookUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


// presentation/screens/booklist/BookListViewModel.kt
@HiltViewModel
class BookListViewModel @Inject constructor(
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val updateBookStatusUseCase: UpdateBookStatusUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<BookListUiState>(BookListUiState.Loading)
    val uiState: StateFlow<BookListUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.value = BookListUiState.Loading
            try {
                val books = getAllBooksUseCase()
                _uiState.value = BookListUiState.Success(
                    books.map { BookUI.fromDomain(it) }
                )
            } catch (e: Exception) {
                _uiState.value = BookListUiState.Error(
                    e.message ?: "Error desconocido"
                )
            }
        }
    }

    fun updateReadStatus(bookId: String, newStatus: ReadStatus) {
        viewModelScope.launch {
            try {
                updateBookStatusUseCase(bookId, newStatus)
                loadBooks() // Recarga la lista después de actualizar
            } catch (e: Exception) {
                _uiState.value = BookListUiState.Error(
                    "Error al actualizar el estado: ${e.message}"
                )
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        // Aquí podrías implementar la búsqueda
    }
}