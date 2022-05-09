package com.example.bookfinderapp.domain.usecase

import com.example.bookfinderapp.data.repository.DbRepository
import com.example.bookfinderapp.domain.UseCase

class ReqDeleteBookMarkUseCase(
    private val dbRepository: DbRepository
) : UseCase {
    suspend operator fun invoke(id: String) = dbRepository.deleteBookMark(id)
}