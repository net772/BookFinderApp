package com.example.bookfinderapp.data.network

import com.example.bookfinderapp.data.response.BookFinderResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("books/v1/volumes?maxResults=30")
    suspend fun getBookData(
        @Query("q") search: String,
        @Query("startIndex") startIndex: Int,
    ) : BookFinderResponse
}