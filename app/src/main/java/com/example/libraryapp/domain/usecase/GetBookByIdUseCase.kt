package com.example.libraryapp.domain.usecase

import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.repository.BookRepository
import javax.inject.Inject

class GetBookByIdUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(id: String): Book? = repository.getBookById(id)
}