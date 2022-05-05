package com.example.bookfinderapp.data.response

import com.google.gson.annotations.SerializedName

data class BookFinderResponse(
    @SerializedName("kind") val kind: String,
    @SerializedName("totalItems")val totalItems: Int,
    @SerializedName("items") val items: List<Book>
)

data class Book(
    @SerializedName("kind") val kind: String,
    @SerializedName("id") val id: String,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo?
)

data class VolumeInfo(
    @SerializedName("title") val title: String,
    @SerializedName("authors") val authors: List<String>,
    @SerializedName("publishedDate") val publishedDate: String,
    @SerializedName("imageLinks") val imageLinks: ImageLink,
    @SerializedName("description") val description: String
)

data class ImageLink(
    val smallThumbnail: String,
    val thumbnail: String
)
