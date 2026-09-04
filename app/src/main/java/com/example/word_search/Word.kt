package com.example.word_search

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey
    val text: String,
    val length: Int
)
