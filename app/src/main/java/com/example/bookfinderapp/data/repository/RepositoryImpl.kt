package com.example.bookfinderapp.data.repository

import android.util.Log
import com.example.bookfinderapp.data.network.ApiService
import com.example.bookfinderapp.data.response.BookFinderResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class RepositoryImpl(
    private val service: ApiService
) : Repository {
    override fun getBookData(search: String, page: Int): Flow<BookFinderResponse> = flow {
        emit(service.getBookData(search, page))
    }.map {
        Log.d("동현", "this : ${it}")
        it
    }
}