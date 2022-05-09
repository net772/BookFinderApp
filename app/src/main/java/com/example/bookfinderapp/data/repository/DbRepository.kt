package com.example.bookfinderapp.data.repository

import com.example.bookfinderapp.data.db.entity.BookMarkEntity
import kotlinx.coroutines.flow.Flow

interface DbRepository {
    fun getAllBookMark(): Flow<List<BookMarkEntity>>
    fun getBookMark(id: String): Flow<BookMarkEntity>
    suspend fun insertBookMark(userLikeEntity: BookMarkEntity)
    suspend fun deleteBookMark(id: String)
}