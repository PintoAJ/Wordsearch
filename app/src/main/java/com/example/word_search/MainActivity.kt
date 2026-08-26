package com.example.word_search

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
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Green),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(10),
            contentPadding = PaddingValues(1.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            items(100) { index ->
                Box(
                    modifier = Modifier
                        .background(Color.White)
                        .clickable(onClick = {
                            println("$index was clicked")
                        }),
                    contentAlignment = Alignment.Center
                ) {
                    val id = index
                    Text("$id")
                }
            }
        }
    }
}

@Composable
fun testClick(id: String){

}

@Preview
@Composable
fun Preview() {
    InitGrid()
    // Text(text = letters[(0..25).random()].toString())
}