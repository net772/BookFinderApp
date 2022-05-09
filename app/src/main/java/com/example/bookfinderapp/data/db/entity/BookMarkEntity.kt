package com.example.bookfinderapp.data.db.entity

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@androidx.room.Entity
data class BookMarkEntity(
    @PrimaryKey val id: String = "",
    val title: String,
    val authors: String,
    val publishedDate: String,
    val imageLinks: String,
    val description: String,
    val infoLink: String,
    val state: Boolean
): Parcelable