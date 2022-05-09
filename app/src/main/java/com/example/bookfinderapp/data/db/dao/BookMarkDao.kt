package com.example.bookfinderapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bookfinderapp.data.db.entity.BookMarkEntity

@Dao
interface BookMarkDao {

    @Query("SELECT * FROM BookMarkEntity")
    suspend fun getAll(): List<BookMarkEntity>

    @Query("SELECT * FROM BookMarkEntity WHERE id=:id")
    suspend fun get(id: String): BookMarkEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userLikeEntity: BookMarkEntity)

    @Query("DELETE FROM BookMarkEntity WHERE id=:id")
    suspend fun delete(id: String)

}