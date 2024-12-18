package com.example.libraryapp.domain.usecase
import javax.inject.Inject
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.model.Book

class AddBookUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(book: Book) = repository.addBook(book)
}
