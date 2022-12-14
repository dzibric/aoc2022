package day8.part2

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
    fun calculateTopScenicScore(): Int {
        var topScenicScore = 0
        for (row in 1 until rows.size - 1) {
            for (column in 1 until rows[row].size - 1) {
                val scenicScore = getScenicScore(rows[row][column], row, column)
                if (scenicScore > topScenicScore) {
                    topScenicScore = scenicScore
                }
            }
        }
        return topScenicScore
    }

    private fun getScenicScore(height: Int, indexX: Int, indexY: Int): Int {
        var rightScore = 0
        for (rightUntilEdge in  indexX + 1 until rows.size) {
            if (height <= rows[rightUntilEdge][indexY]) {
                rightScore++
                break
            }
            rightScore++
        }
        var leftScore = 0
        for (leftUntilEdge in  indexX - 1 downTo  0) {
            if (height <= rows[leftUntilEdge][indexY]) {
                leftScore++
                break
            }
            leftScore++
        }
        var topScore = 0
        for (topUntilEdge in  indexY - 1 downTo  0) {
            if (height <= rows[indexX][topUntilEdge]) {
                topScore++
                break
            }
            topScore++
        }
        var bottomScore = 0
        for (downUntilEdge in  indexY + 1 until rows[indexX].size) {
            if (height <= rows[indexX][downUntilEdge]) {
                bottomScore++
                break
            }
            bottomScore++
        }
        return rightScore * leftScore * topScore * bottomScore
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
    val input = readFile("src/day10.part2.day11.part2.day13.part1.day12.part1.main/kotlin/day8/input.txt")
    val trees = generateGrid(input)
    println(trees.calculateTopScenicScore())
}