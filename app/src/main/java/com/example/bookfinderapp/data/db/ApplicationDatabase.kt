package com.example.bookfinderapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bookfinderapp.data.db.dao.BookMarkDao
import com.example.bookfinderapp.data.db.entity.BookMarkEntity

@Database(
    entities = [BookMarkEntity::class],
    version = 1,
    exportSchema = false
)

abstract class ApplicationDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "ApplicationDataBase.db"
    }

    abstract fun bookMarkDao() : BookMarkDao
}