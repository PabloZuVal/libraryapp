package com.example.libraryapp.domain.usecase
import javax.inject.Inject
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.model.Book

class GetAllBooksUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(): List<Book> = repository.getAllBooks()
}