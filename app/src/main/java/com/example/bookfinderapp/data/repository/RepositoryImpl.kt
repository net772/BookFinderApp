package com.example.bookfinderapp.data.repository

import com.example.bookfinderapp.data.network.ApiService
import com.example.bookfinderapp.data.response.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val service: ApiService
) : Repository {
    override fun getBookData(search: String, page: Int): Flow<List<Book>> = flow {
        emit(service.getBookData(search, page))
    }.map {
        it.items
    }
}