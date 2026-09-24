package com.example.word_search

import kotlin.enums.enumEntries

enum class Direction(val dx: Int, val dy: Int) {
    Down(0, -1),
    Right(1, 0),
    Up(0, 1),
    Left(-1, 0);

    companion object {
        fun getDirection(gridLength: Int, row: Int, col: Int): List<Pair<Direction,Int>> {
            val directions = mutableListOf<Pair<Direction,Int>>()

            var len = gridLength - row
            if (len > 2) {
                directions.add(Pair(Down,len))
            }

            len = gridLength - col
            if (len > 2) {
                directions.add(Pair(Right, len))
            }

            if (row > 2) {
                directions.add(Pair(Up,row))
            }

            if (col > 2) {
                directions.add(Pair(Left,col))
            }

            return directions
        }
    }
}