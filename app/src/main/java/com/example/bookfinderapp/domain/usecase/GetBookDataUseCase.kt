package com.example.bookfinderapp.domain.usecase

import com.example.bookfinderapp.data.repository.ApiRepository
import com.example.bookfinderapp.domain.UseCase

class GetBookDataUseCase(
    private val apiRepository: ApiRepository
) : UseCase {
    operator fun invoke(search: String, page: Int, maxCount: Int) = apiRepository.getBookData(search, page, maxCount)
}