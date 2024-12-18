package com.example.libraryapp.domain.usecase

import androidx.room.Query
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.model.Book
import javax.inject.Inject

class SearchBooksUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(query: String): List<Book> =
        repository.searchBooks(query)
}
