package com.example.word_search

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.word_search.ui.theme.Word_SearchTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.word_search.GridItem
import android.util.Log
import androidx.activity.viewModels
import androidx.collection.mutableIntListOf
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import java.io.File
import com.example.word_search.Direction

class MainActivity : ComponentActivity() {
//    private val db by lazy {
//        Room.databaseBuilder(
//            applicationContext,
//            WordDatabase::class.java,
//            "words.db"
//        ).build()
//    }

//    private val viewModel by viewModels<WordViewModel>(
//        factoryProducer = {
//            object : ViewModelProvider.Factory {
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return WordViewModel(db.dao) as T
//                }
//            }
//        }
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Word_SearchTheme {
                val wordMap: MutableMap<Int, MutableList<String>> = loadWords(this, "wordbank.txt")
                Log.d("test", "Length: " + wordMap.size)

                for ((num, list) in wordMap) {
                    Log.d("test", "$num: " + list[0] + ", " + list[list.size - 1])
                }

                InitGrid(wordMap)
            }
        }
    }
}

/**
 * Loads words from the word bank and sorts them into a dictionary by length
 */
fun loadWords(context: Context, filename: String): MutableMap<Int, MutableList<String>> {
    var wordList = listOf<String>()
    val wordMap = mutableMapOf<Int, MutableList<String>>()

    // read text file for list of words
    try {
        context.assets.open(filename).bufferedReader().useLines { lines ->
            wordList = lines.toList() // Converts each line of the file into a list element
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    // organize words by length
    for (word in wordList) {

        // words must be 3 to 10 letters long
        if (word.length in 3..10) {

            if (word.length in wordMap) {
                wordMap[word.length]?.add(word)
            } else {
                wordMap[word.length] = mutableListOf(word)
            }
        }
    }

    return wordMap
}

@Composable
fun InitGrid(wordMap: MutableMap<Int, MutableList<String>>) {
    val gridLength = 10
    val targetWords = 5
    //val gridData: List<GridData> = remember { getLetters(wordMap, gridLength) }
    val gridData: List<GridData> = getLetters(wordMap, gridLength, targetWords)
    val bgColor = Color.Green

    var id by remember { mutableIntStateOf(-1) }
    var id2 by remember { mutableIntStateOf(-1) }
    var cell by remember { mutableIntStateOf(-1) }
    var cell2 by remember { mutableIntStateOf(-1) }
    val pairs = remember { mutableStateListOf<List<Int>>() }

    // test v
    //pairs.add(listOf(1,6))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = bgColor),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(gridLength),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.White)
        ) {
            items(gridData, key = {item -> item.id}) {data ->
                GridItem(
                    item = data,
                    onClick = {
//                        cur_pair.add(data.id)
                        if (id == -1) {
                            id = data.id
                        } else {
                            id2 = data.id
                        }

                    }
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(top = 20.dp)
                .height(100.dp)
                .width(200.dp)
                .background(Color.LightGray)
                .align(Alignment.TopCenter)
        ) {
            // check for valid pair
            if (id != -1 && id2 != -1) {
                for (pair in pairs) {
                    if (pair[0] == id && pair[1] == id2) {
                        cell = id
                        cell2 = id2
                        break
                    }
                }

                id = -1
                id2 = -1
            }

            if (cell != -1 && cell2 != -1){
                Text("id: $id, id2: $id2 \nWord found between grid cells #$cell and #$cell2")
            }
        }
    }
}

/**
 * Uses word bank to populate grid of letters
 */
fun getLetters(wordMap: MutableMap<Int, MutableList<String>>, gridLength: Int, targetWords: Int): List<GridData> {
    val rows = mutableListOf<StringBuilder>()
    val items = mutableListOf<GridData>()
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val pairs = mutableStateListOf<List<Int>>()

    // initialize empty grid
    for (i in 1..gridLength) {
        rows.add(StringBuilder(".".repeat(gridLength)))
    }

    // add words into the empty grid
    var count = 0

    while (count < targetWords) {
        val row = (0..<gridLength).random()
        val col = (0..<gridLength).random()
        var wordFound = false

        Log.d("test", rows.toString())
        Log.d("test", "Point: ($row,$col)")

        // determine direction
        val directions = Direction.getDirection(gridLength, row, col).shuffled()
        Log.d("test", directions.toString())

        for ((dir, len) in directions) {
            val constraints = mutableMapOf<Int, String>()

            // calculate letter constraints
            for (i in (0..<len)) {
                val r2 = row + (dir.dy * i)
                val c2 = col + (dir.dx * i)

                Log.d("test", "i: $i")
                Log.d("test", "r2,c2: ($r2,$c2)")

                val letter = rows[r2][c2].toString()

                if (letter != ".") {
                    constraints[i] = letter
                }
            }

            // check all words of that length
            val potentialWords = wordMap[len]?.shuffled()

            if (potentialWords != null) {
                for (word in potentialWords) {
                    if (constraints.isEmpty() || verifyWord(constraints.toMap(), word)) {

                        for (i in (0..<len)) {
                            val r2 = row + (dir.dy * i)
                            val c2 = col + (dir.dx * i)

                            rows[r2][c2] = word[i]
                        }

                        // store coordinate points
                        val p1 = (row * gridLength) + col + 1   // Note: Grid coordinates converted from 0-based string to 1-based grid system Ex. [0][0] --> index 1
                        val dy = dir.dy * len
                        val dx = dir.dx * len
                        val p2 = ((row + dy) * gridLength) + (col + dx) + 1

                        pairs.add(listOf(p1, p2))

                        wordFound = true
                        count++
                    }

                    if (wordFound) {
                        break
                    }
                }
            }

            if (wordFound) {
                break
            }
        }
    }

    // converts list of StringBuilder's to GridData
    var index = 1

    for (row in rows) {
        for (char in row) {
            var c = char.toString()

            if (c == ".") {
                c = letters.random().toString()
            }

            items.add(GridData(index, c))
            index++
        }
    }

    return items
}

/**
 * Checks if a word fits the required letter constraints
 */
fun verifyWord(constraints: Map<Int, String>, word: String): Boolean {
    for ((index, letter) in constraints) {
        if (word[index].toString() != letter) {
            return false
        }
    }

    return true
}

/**
 * For testing purposes
 */
fun randomLetters(gridLength: Int): List<GridData> {
    val items = mutableListOf<GridData>()
    val gridSize = gridLength * gridLength
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    for (i in 1..gridSize){
        items.add(GridData(i, letters[(0..25).random()].toString()))
    }

    return items
}

@Preview
@Composable
fun Preview() {
    //val wordMap: MutableMap<Int, MutableList<String>> = loadWords(this, "wordbank.txt")
    //InitGrid(wordMap)
}