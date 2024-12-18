package com.example.libraryapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books") // Table name
data class BookEntity(
    @PrimaryKey
    val id: String = "",
    val title: String,
    val author: String,
    val isbn: String,
    @ColumnInfo(name = "read_status")
    val readStatus: String,
    val description: String = "",
    @ColumnInfo(name = "cover_url")
    val coverUrl: String = "",
    @ColumnInfo(name = "added_date")
    val addedDate: Long
)