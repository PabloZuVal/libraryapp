package com.example.libraryapp.data.local.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.libraryapp.data.local.dao.BookDao
import com.example.libraryapp.data.local.entity.BookEntity

@Database(
    entities = [BookEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}