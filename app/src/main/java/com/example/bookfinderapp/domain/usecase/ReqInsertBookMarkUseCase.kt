package com.example.bookfinderapp.domain.usecase

import com.example.bookfinderapp.data.db.entity.BookMarkEntity
import com.example.bookfinderapp.data.repository.DbRepository
import com.example.bookfinderapp.domain.UseCase

class ReqInsertBookMarkUseCase(
    private val dbRepository: DbRepository
) : UseCase {
    suspend operator fun invoke(bookMarkEntity: BookMarkEntity) = dbRepository.insertBookMark(bookMarkEntity)
}