package day9.part1

import day9.part1.SpotType.*
import readFile
import kotlin.math.abs

enum class SpotType(val symbol: Char) {
    HEAD('H'),
    TAIL('T'),
    EMPTY('.')
}

enum class Direction(val code: Char) {
    UP('U'),
    DOWN('D'),
    LEFT('L'),
    RIGHT('R');
    companion object {
        fun fromCode(code: Char): Direction = values().find { it.code == code } ?: UP
    }
}

class Spot(var spotType: SpotType = EMPTY, var visited: Boolean = false) {
    fun printSpot() = print(spotType.symbol + " ")
}

class Field(val field: MutableList<MutableList<Spot>> = mutableListOf()) {
    var headPosition = Pair(1, 1)
    var tailPosition = Pair(1, 1)

    init {
        for (index in 0..1000) {
            field.add(MutableList(1000) { Spot() })
        }
        headPosition = Pair(field.size / 2, field.size / 2)
        tailPosition = Pair(field.size / 2, field.size / 2)
    }

    fun moveHead(movements: Int, direction: Direction) {
        for (move in 0 until movements) {
            val prevPositionX = headPosition.first
            val prevPositionY = headPosition.second
            headPosition = when (direction) {
                Direction.UP -> Pair(headPosition.first - 1, headPosition.second)
                Direction.DOWN -> Pair(headPosition.first + 1, headPosition.second)
                Direction.LEFT -> Pair(headPosition.first, headPosition.second - 1)
                Direction.RIGHT -> Pair(headPosition.first, headPosition.second + 1)
            }
            moveTail(prevPositionX, prevPositionY)
        }
    }

    fun moveTail(previousHeadPositionX: Int, previousHeadPositionY: Int) {
        if (abs(headPosition.first - tailPosition.first) >= 2 || abs(headPosition.second - tailPosition.second) >= 2) {
            tailPosition = Pair(previousHeadPositionX, previousHeadPositionY)
            field[previousHeadPositionX][previousHeadPositionY].visited = true
        }
    }

    fun markField(x: Int, y: Int, spotType: SpotType) {
        field[x][y].spotType = spotType
    }

    private fun expandField(by: Int) {
        for (time in 0 until by) {
            field.add(0, MutableList(field[0].size) { Spot() })
            field.add(field.size - 1, MutableList(field[0].size) { Spot() })
        }
        field.forEach {
            for (time in 0 until by) {
                it.add(0, Spot())
                it.add(it.size - 1, Spot())
            }
        }
        headPosition = Pair(headPosition.first + by, headPosition.second + by)
        tailPosition = Pair(tailPosition.first + by, tailPosition.second + by)
    }

    fun printField() {
        field.forEachIndexed { xIndex, row ->
            row.forEachIndexed { yIndex, column ->
                if (tailPosition.first == xIndex && tailPosition.second == yIndex) {
                    print(TAIL.symbol + " ")
                } else if (headPosition.first == xIndex && headPosition.second == yIndex) {
                    print(HEAD.symbol + " ")
                } else if (xIndex == field.size / 2 && yIndex == field.size / 2) {
                    print("S ")
                } else {
                    print(EMPTY.symbol + " ")
                }
            }
            println()
        }
        println()
    }

    fun printVisited() {
        field.forEachIndexed { xIndex, row ->
            row.forEachIndexed { yIndex, column ->
                if (field[xIndex][yIndex].visited) {
                    print("# ")
                } else {
                    print(EMPTY.symbol + " ")
                }
            }
            println()
        }
        println()
    }

    fun countVisited(): Int {
        return field.sumBy { row -> row.sumBy { if (it.visited) 1 else 0 }} + 1
    }
}

fun main(args: Array<String>) {
    val input = readFile("src/day10.part2.day11.part2.main/kotlin/day9/input.txt")
    val field = Field()
    input.forEach { movement ->
        val info = movement.split(" ")
        val direction = Direction.fromCode(info[0].toCharArray()[0])
        val movements = info[1].toInt()
       // println(info)
        field.moveHead(movements, direction)
       // field.printField()
    }
   // field.printVisited()
    println(field.countVisited())
}