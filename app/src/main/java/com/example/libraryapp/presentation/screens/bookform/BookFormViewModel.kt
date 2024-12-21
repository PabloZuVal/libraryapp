// presentation/screens/bookform/BookFormViewModel.kt
package com.example.libraryapp.presentation.screens.bookform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.usecase.AddBookUseCase
import com.example.libraryapp.domain.usecase.GetBookByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookFormViewModel @Inject constructor(
    private val addBookUseCase: AddBookUseCase,
    private val getBookByIdUseCase: GetBookByIdUseCase
) : ViewModel() {

    suspend fun getBook(id: String): Book? {
        return try {
            getBookByIdUseCase(id)
        } catch (e: Exception) {
            null
        }
    }

    fun saveBook(book: Book) {
        viewModelScope.launch {
            try {
                addBookUseCase(book)
            } catch (e: Exception) {
                // Manejar el error
            }
        }
    }
}