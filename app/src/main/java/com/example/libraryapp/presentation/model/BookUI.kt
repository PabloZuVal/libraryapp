package com.example.libraryapp.presentation.model
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.ReadStatus

data class BookUI(
    val id: String = "",
    val title: String,
    val author: String,
    val readStatus: ReadStatus,
    val coverUrl: String = "",
    val description: String = ""
) {
    companion object {
        fun fromDomain(book: Book) = BookUI(
            id = book.id,
            title = book.title,
            author = book.author,
            readStatus = book.readStatus,
            coverUrl = book.coverUrl,
            description = book.description
        )
    }
}