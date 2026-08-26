package com.example.word_search

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box

data class GridItem(
    val id: Int,
    val letter: String,
    val color: Color = Color.White
)

@Composable
fun Item(
    modifier: Modifier = Modifier,
    item: GridItem
) {
    Box(
        modifier = Modifier
            .background(item.color)
    )
}
