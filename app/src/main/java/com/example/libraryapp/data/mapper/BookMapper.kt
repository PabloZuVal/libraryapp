package com.example.libraryapp.data.mapper
import com.example.libraryapp.data.local.entity.BookEntity
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.ReadStatus
import javax.inject.Inject


class BookMapper @Inject constructor() {
    fun toEntity(book: Book): BookEntity {
        return BookEntity(
            id = book.id,
            title = book.title,
            author = book.author,
            isbn = book.isbn,
            readStatus = book.readStatus.name,
            description = book.description,
            coverUrl = book.coverUrl,
            addedDate = book.addedDate
        )
    }

    fun toDomain(entity: BookEntity): Book {
        return Book(
            id = entity.id,
            title = entity.title,
            author = entity.author,
            isbn = entity.isbn,
            readStatus = ReadStatus.valueOf(entity.readStatus),
            description = entity.description,
            coverUrl = entity.coverUrl,
            addedDate = entity.addedDate
        )
    }
}