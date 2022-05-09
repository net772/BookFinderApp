package com.example.bookfinderapp.data.repository

import com.example.bookfinderapp.data.db.dao.BookMarkDao
import com.example.bookfinderapp.data.db.entity.BookMarkEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DbRepositoryImpl(
    private val bookMarkDao: BookMarkDao
) : DbRepository {
    override fun getAllBookMark(): Flow<List<BookMarkEntity>> = flow {
        emit(bookMarkDao.getAll())
    }.map { it }

    override fun getBookMark(id: String): Flow<BookMarkEntity> = flow {
        emit(bookMarkDao.get(id))
    }.map { it }

    override suspend fun insertBookMark(bookMarkEntity: BookMarkEntity) {
        bookMarkDao.insert(bookMarkEntity)
    }

    override suspend fun deleteBookMark(id: String) {
        bookMarkDao.delete(id)
    }

}