package com.example.libraryapp.data.repository
import com.example.libraryapp.data.mapper.BookMapper
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.repository.BookRepository
import javax.inject.Inject
import com.example.libraryapp.domain.model.ReadStatus
import com.example.libraryapp.data.local.dao.BookDao

class BookRepositoryImpl @Inject constructor(
    private val bookDao: BookDao,
    private val bookMapper: BookMapper
) : BookRepository {

    override suspend fun getAllBooks(): List<Book> {
        return bookDao.getAllBooks().map {bookEntity ->
            bookMapper.toDomain(bookEntity) }
    }

    override suspend fun getBookById(id: String): Book? {
        return bookDao.getBookById(id)?.let { bookMapper.toDomain(it) }
    }

    override suspend fun addBook(book: Book) {
        bookDao.insertBook(bookMapper.toEntity(book))
    }

    override suspend fun updateBook(book: Book) {
        bookDao.updateBook(bookMapper.toEntity(book))
    }

    override suspend fun deleteBook(id: String) {
        bookDao.getBookById(id)?.let { bookDao.deleteBook(it) }
    }

    override suspend fun updateReadStatus(id: String, status: ReadStatus) {
        getBookById(id)?.let { book ->
            updateBook(book.copy(readStatus = status))
        }
    }

    override suspend fun searchBooks(query: String): List<Book> {
        return bookDao.searchBooks(query).map { bookMapper.toDomain(it) }
    }
}