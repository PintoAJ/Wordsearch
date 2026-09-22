package com.example.word_search

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
        val dir = System.getProperty("user.dir")
        Log.d("test", dir.toString())

        val folder = File(dir)
        Log.d("test", folder.name)

        folder.listFiles()?.forEach { file ->
            Log.d("test", file.name)
        }

        val result = folder.listFiles()?.filter { it.isFile }?: emptyList()
        Log.d("test", result.toString())

//        val file = File("/app/src/main/res/wordbank.txt")
//
//        for (line in file.readLines()) {
//            Log.d("test", line)
//            break
//        }
//        viewModelScope.launch {}
    }
}