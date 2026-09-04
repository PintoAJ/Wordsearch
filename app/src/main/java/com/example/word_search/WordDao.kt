package com.example.word_search

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface WordDao {

    @Upsert
    suspend fun insertWord(word: Word)

    @Query("SELECT * FROM words WHERE length = :length")
    suspend fun getWordsByLength(length: Int): List<Word>
}