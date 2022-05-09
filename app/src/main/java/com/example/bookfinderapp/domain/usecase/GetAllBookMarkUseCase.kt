package com.example.bookfinderapp.domain.usecase

import com.example.bookfinderapp.data.repository.DbRepository
import com.example.bookfinderapp.domain.UseCase

class GetAllBookMarkUseCase(
    private val dbRepository: DbRepository
) : UseCase {
    operator fun invoke() = dbRepository.getAllBookMark()
}