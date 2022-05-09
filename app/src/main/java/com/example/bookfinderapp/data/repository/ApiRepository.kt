package com.example.bookfinderapp.data.repository

import com.example.bookfinderapp.data.response.BookFinderResponse
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    fun getBookData(search: String, page: Int, maxCount: Int): Flow<BookFinderResponse>
}