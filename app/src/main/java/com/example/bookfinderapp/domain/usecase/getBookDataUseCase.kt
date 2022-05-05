package com.example.bookfinderapp.domain.usecase

import com.example.bookfinderapp.data.repository.Repository
import com.example.bookfinderapp.domain.UseCase

class GetBookDataUseCase(
    private val productRepository: Repository
) : UseCase {
    operator fun invoke(search: String, page: Int) = productRepository.getBookData(search, page)
}