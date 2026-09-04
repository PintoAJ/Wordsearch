package com.example.word_search

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
import androidx.collection.mutableIntListOf
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Word_SearchTheme {
                InitGrid()
            }
        }
    }
}

@Composable
fun InitGrid() {
    val gridLength = 10
    val gridData: List<GridData> = remember { testGrid(gridLength) }
    val bgColor = Color.Green

    var id by remember { mutableIntStateOf(-1) }
    var id2 by remember { mutableIntStateOf(-1) }
    var cell by remember { mutableIntStateOf(-1) }
    var cell2 by remember { mutableIntStateOf(-1) }
    var pairs = remember { mutableStateListOf<List<Int>>() }

    // test values
    pairs.add(listOf(1,6))

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

/**
 * For testing purposes. Tests the grid with words
 */
fun testGrid(gridLength: Int): List<GridData> {
    val rows = mutableListOf<StringBuilder>()
    val items = mutableListOf<GridData>()
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    for (i in 1..gridLength) {
        rows.add(StringBuilder(".".repeat(gridLength)))
    }

    // add words at predetermined locations
    var temp = "BANANA"

    for (i in 0..temp.length-1) {
        rows[0][i] = temp[i]
    }

    // converts list of StringBuilder's to GridData
    var count = 1

    for (row in rows) {
        for (char in row) {
            var c = char.toString()

            if (c == ".") {
                c = letters[(0..25).random()].toString()
            }

            items.add(GridData(count, c))
            count++
        }
    }

    return items
}

@Preview
@Composable
fun Preview() {
    InitGrid()
}