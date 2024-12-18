package com.example.libraryapp.domain.repository

import com.example.libraryapp.domain.model.ReadStatus
import com.example.libraryapp.domain.model.Book

interface BookRepository {
    suspend fun getAllBooks(): List<Book>
    suspend fun getBookById(id: String): Book?
    suspend fun addBook(book: Book)
    suspend fun updateBook(book: Book)
    suspend fun deleteBook(id: String)
    suspend fun updateReadStatus(id: String, status: ReadStatus)
    suspend fun searchBooks(query: String): List<Book>
}