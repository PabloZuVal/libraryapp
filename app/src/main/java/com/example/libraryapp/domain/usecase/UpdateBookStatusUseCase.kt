package com.example.libraryapp.domain.usecase

import com.example.libraryapp.domain.repository.BookRepository
import javax.inject.Inject
import com.example.libraryapp.domain.model.ReadStatus

class UpdateBookStatusUseCase @Inject constructor(
    private val repository: BookRepository
) {
    suspend operator fun invoke(id: String, status: ReadStatus) = repository.updateReadStatus(id, status)
}