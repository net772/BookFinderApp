package com.example.bookfinderapp.domain.usecase

import com.example.bookfinderapp.data.repository.DbRepository
import com.example.bookfinderapp.domain.UseCase

class GetBookMarkUseCase(
    private val dbRepository: DbRepository
) : UseCase {
    operator fun invoke(id: String) = dbRepository.getBookMark(id)
}