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
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
    val gridLength = 5
    val gridData: List<GridData> = remember { getLetters(gridLength * gridLength)}
    val bgColor = Color.Green
    var id by remember { mutableIntStateOf(-1) }
    var letter by remember {mutableStateOf("")}


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = bgColor),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(gridLength),
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.White)
        ) {
            items(gridData, key = {item -> item.id}) {data ->
                GridItem(
                    item = data,
                    onClick = {
                        id = data.id
                        letter = data.letter
                    }
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(200.dp)
                .background(Color.LightGray)
                .align(Alignment.TopCenter)
        ) {
            if (id != -1 && letter != ""){
                Text("GridCell #$id: $letter")
            }
        }
    }
}

fun getLetters(gridSize: Int): List<GridData> {
    val items = mutableListOf<GridData>()
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    for (i in 1..gridSize){
        items.add(GridData(i, letters[(0..25).random()].toString()))
    }

    return items
}

@Preview
@Composable
fun Preview() {
    InitGrid()
}