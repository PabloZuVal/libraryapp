package com.example.libraryapp.domain.model
import com.example.libraryapp.domain.model.ReadStatus

data class Book(
    val id: String = "",
    val title: String,
    val author: String,
    val isbn: String,
    val readStatus: ReadStatus,
    val description: String = "",
    val coverUrl: String = "",
    val addedDate: Long = System.currentTimeMillis()
)