package com.example.word_search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.File
import android.util.Log

class WordViewModel(
    private val dao: WordDao
): ViewModel() {

    fun initDB() {
        // route: app/src/main/res/wordbank.txt
        val file = File("app/src/main/res/wordbank.txt")
        Log.d("test", file.name)

        Log.d("test", "Exists: " + file.exists())
        Log.d("test","Path: "+ file.path)
        Log.d("test","Can Read: " + file.canRead().toString())

        val currentPath = System.getProperty("user.dir")
        Log.d("test", "Current Directory: $currentPath")

//        val file = File("/app/src/main/res/wordbank.txt")
//
//        for (line in file.readLines()) {
//            Log.d("test", line)
//            break
//        }
//        viewModelScope.launch {}
    }
}