package com.example.bookfinderapp.data.repository

import com.example.bookfinderapp.data.network.ApiService
import com.example.bookfinderapp.data.response.BookFinderResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ApiRepositoryImpl(
    private val service: ApiService
) : ApiRepository {
    override fun getBookData(search: String, page: Int, maxCount:Int): Flow<BookFinderResponse> = flow {
        emit(service.getBookData(search, page, maxCount))
    }.map { it }
}