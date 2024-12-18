package com.example.libraryapp.di
import android.content.Context
import androidx.room.Room
import com.example.libraryapp.data.local.dao.BookDao
import com.example.libraryapp.data.local.database.AppDatabase
import com.example.libraryapp.data.repository.BookRepositoryImpl
import com.example.libraryapp.domain.repository.BookRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.android.components.ApplicationComponent
import com.example.libraryapp.data.mapper.BookMapper

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "books_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookDao(database: AppDatabase): BookDao {
        return database.bookDao()
    }

    @Provides
    @Singleton
    fun provideBookRepository(
        bookDao: BookDao,
        bookMapper: BookMapper
    ): BookRepository {
        return BookRepositoryImpl(bookDao, bookMapper)
    }
}