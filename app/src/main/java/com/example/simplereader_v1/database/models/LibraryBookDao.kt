package com.example.simplereader_v1.database.models

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LibraryBookDao {
    @Query("SELECT * FROM library_book")
    suspend fun getLibraryBooks() : List<LibraryBook>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBook(book: LibraryBook)

    @Update
    suspend fun update(book: LibraryBook)

    @Delete
    suspend fun delete(book: LibraryBook)
}