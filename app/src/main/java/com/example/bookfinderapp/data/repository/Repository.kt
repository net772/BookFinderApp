package com.example.bookfinderapp.data.repository

import com.example.bookfinderapp.data.response.Book
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getBookData(search: String, page: Int): Flow<List<Book>>
}