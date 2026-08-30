package com.example.word_search

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text

data class GridData(
    val id: Int,
    val letter: String,
)

@Composable
fun GridButton(
    letter: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .background(Color.White),
        colors = ButtonColors(Color.White,
            Color.Black,
            Color.White,
            Color.Black)){
        Text(text = letter)
    }
}

@Composable
fun GridItem(
    modifier: Modifier = Modifier,
    item: GridData,
    onClick: () -> Unit
) {
    GridButton(item.letter, onClick)
}
