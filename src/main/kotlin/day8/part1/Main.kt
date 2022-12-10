package day8.part1

import readFile

class Grid(val rows: List<List<Int>>) {
    fun printGrid() {
        rows.forEach { row ->
            row.forEach { column ->
                print(column)
            }
            println()
        }
    }
    fun calculateVisibleTrees(): Int {
        val edges = 2 * rows.size + 2 *rows[0].size - 4
        var visibleTrees = 0
        for (row in 1 until rows.size - 1) {
            for (column in 1 until rows[row].size - 1) {
                if (isTreeVisible(rows[row][column], row, column)) {
                    visibleTrees++
                }
            }
        }
        return edges + visibleTrees
    }

    private fun isTreeVisible(height: Int, indexX: Int, indexY: Int): Boolean {
        var visibleFromRight = true
        for (rightUntilEdge in  indexX + 1 until rows.size) {
            if (height <= rows[rightUntilEdge][indexY]) {
                visibleFromRight = false
                break
            }
        }
        var visibleFromLeft = true
        for (leftUntilEdge in  indexX - 1 downTo  0) {
            if (height <= rows[leftUntilEdge][indexY]) {
                visibleFromLeft = false
                break
            }
        }
        var visibleFromTop = true
        for (topUntilEdge in  indexY - 1 downTo  0) {
            if (height <= rows[indexX][topUntilEdge]) {
                visibleFromTop = false
                break
            }
        }
        var visibleFromBottom = true
        for (downUntilEdge in  indexY + 1 until rows[indexX].size) {
            if (height <= rows[indexX][downUntilEdge]) {
                visibleFromBottom = false
                break
            }
        }
        return visibleFromBottom || visibleFromTop || visibleFromLeft || visibleFromRight
    }
}

fun generateGrid(input: List<String>): Grid {
    val rows = mutableListOf<List<Int>>()
    input.forEach {
        rows.add(it.toCharArray().map { chars -> chars.digitToInt() }.toList())
    }
    return Grid(rows)
}

fun main(args: Array<String>) {
    val input = readFile("src/day10.part2.main/kotlin/day8/input.txt")
    val trees = generateGrid(input)
    println(trees.calculateVisibleTrees())
}