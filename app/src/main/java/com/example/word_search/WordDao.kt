package com.example.word_search

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface WordDao {

    @Upsert
    suspend fun insertWord(word: Word)

    @Query("SELECT * FROM word WHERE length = :length")
    suspend fun getWordsByLength(length: Int): List<Word>

    @Query("SELECT EXISTS(SELECT 1 FROM word LIMIT 1)")
    suspend fun isPopulated(): Boolean
}