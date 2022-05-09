package com.example.bookfinderapp.data.response

import android.os.Parcelable
import com.example.bookfinderapp.data.db.entity.BookMarkEntity
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookFinderResponse(
    @SerializedName("kind") val kind: String = "",
    @SerializedName("totalItems")val totalItems: Int = 0,
    @SerializedName("items") val items: List<Book> = listOf()
): Parcelable

@Parcelize
data class Book(
    @SerializedName("kind") val kind: String = "",
    @SerializedName("id") val id: String = "",
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo = VolumeInfo()
): Parcelable


@Parcelize
data class VolumeInfo(
    @SerializedName("title") val title: String = "",
    @SerializedName("authors") val authors: List<String> = listOf(),
    @SerializedName("publishedDate") val publishedDate: String = "",
    @SerializedName("imageLinks") val imageLinks: ImageLinks = ImageLinks("",""),
    @SerializedName("description") val description: String = "",
    @SerializedName("infoLink") val infoLink: String = ""
): Parcelable {
        fun toEntity() = BookMarkEntity(
            id = "$title + $authors",
            title = title,
            authors = authors.toString(),
            publishedDate = publishedDate,
            imageLinks = imageLinks.thumbnail,
            description = description,
            infoLink = infoLink,
            state = false
        )
}

@Parcelize
data class ImageLinks(
    val smallThumbnail: String = "",
    val thumbnail: String = ""
): Parcelable

