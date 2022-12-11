package day9.part2

import day9.part2.SpotType.*
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

class Spot(var visited: Boolean = false)

class Rope(var xPos: Int, var yPos: Int, var prevXPos: Int, var prevYPos: Int)

class Field(val field: MutableList<MutableList<Spot>> = mutableListOf()) {
    var ropeHead = Rope(1, 1, 1, 1)
    val ropeTails: MutableList<Rope>

    init {
        for (index in 0..1000) {
            field.add(MutableList(1000) { Spot() })
        }
        ropeHead = Rope(field.size / 2, field.size / 2, field.size / 2, field.size / 2)
        ropeTails = MutableList(9) { Rope(field.size / 2, field.size / 2, field.size / 2, field.size / 2) }
    }

    fun moveHead(movements: Int, direction: Direction) {
        for (move in 0 until movements) {
            val prevXPos = ropeHead.xPos
            val prevYPos = ropeHead.yPos
            ropeHead = when (direction) {
                Direction.UP -> Rope(ropeHead.xPos - 1, ropeHead.yPos, prevXPos, prevYPos)
                Direction.DOWN -> Rope(ropeHead.xPos + 1, ropeHead.yPos, prevXPos, prevYPos)
                Direction.LEFT -> Rope(ropeHead.xPos, ropeHead.yPos - 1, prevXPos, prevYPos)
                Direction.RIGHT -> Rope(ropeHead.xPos, ropeHead.yPos + 1, prevXPos, prevYPos)
            }
            moveTail(ropeHead)
        }
    }

    fun moveTail(ropeHead: Rope, index: Int = 0) {
        if (index >= ropeTails.size) {
            return
        }
        val prevXPos = ropeHead.prevXPos
        val prevYPos = ropeHead.prevYPos
        val ropeTail = ropeTails[index]
        if (abs(ropeTail.xPos - ropeHead.xPos) > 1 || abs(ropeTail.yPos - ropeHead.yPos) > 1) {
            if (ropeHead.xPos == ropeTail.xPos || ropeHead.yPos == ropeTail.yPos) {
                val prevRopeXPos = ropeTail.xPos
                val prevRopeYPos = ropeTail.yPos
                if (ropeHead.xPos != prevXPos && ropeHead.yPos != prevYPos) {
                    val newXPos = if (ropeHead.xPos != ropeTail.xPos) prevXPos else ropeTail.xPos
                    val newYPos = if (ropeHead.yPos != ropeTail.yPos) prevYPos else ropeTail.yPos
                    ropeTails[index] = Rope(newXPos, newYPos, prevRopeXPos, prevRopeYPos)
                    if (index == 8) {
                        field[newXPos][newYPos].visited = true
                    }
                } else {
                    ropeTails[index] = Rope(prevXPos, prevYPos, prevRopeXPos, prevRopeYPos)
                    if (index == 8) {
                        field[prevXPos][prevYPos].visited = true
                    }
                }
            } else {
                val newXPos = when {
                    (ropeTail.xPos + 1 == ropeHead.xPos) -> ropeTail.xPos + 1
                    (ropeTail.xPos - 1 == ropeHead.xPos) -> ropeTail.xPos - 1
                    else -> prevXPos
                }
                val newYPos = when {
                    (ropeTail.yPos + 1 == ropeHead.yPos) -> ropeTail.yPos + 1
                    (ropeTail.yPos - 1 == ropeHead.yPos) -> ropeTail.yPos - 1
                    else -> prevYPos
                }
                ropeTails[index] = Rope(newXPos, newYPos, ropeTail.xPos, ropeTail.yPos)
                if (index == 8) {
                    field[newXPos][newYPos].visited = true
                }
            }
        }
        moveTail(ropeTails[index], index + 1)

    }

    fun printField() {
        field.forEachIndexed { xIndex, row ->
            row.forEachIndexed { yIndex, column ->
                if (ropeTails.any { it.xPos == xIndex && it.yPos == yIndex }) {
                    print((ropeTails.indexOfFirst { it.xPos == xIndex && yIndex == it.yPos } + 1).toString() + "")
                } else if (ropeHead.xPos == xIndex && ropeHead.yPos == yIndex) {
                    print(HEAD.symbol + "")
                } else if (xIndex == field.size / 2 && yIndex == field.size / 2) {
                    print("S")
                } else {
                    print(EMPTY.symbol + "")
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
                    print("#")
                } else {
                    print(EMPTY.symbol + "")
                }
            }
            println()
        }
        println()
    }

    fun countVisited(): Int {
        return field.sumBy { row -> row.sumBy { if (it.visited) 1 else 0 } } + 1
    }
}

fun main(args: Array<String>) {
    val input = readFile("src/day10.part2.day11.part2.main/kotlin/day9/input.txt")
    val field = Field()
    input.forEach { movement ->
        val info = movement.split(" ")
        val direction = Direction.fromCode(info[0].toCharArray()[0])
        val movements = info[1].toInt()
        field.moveHead(movements, direction)
    }
    field.printVisited()
    println(field.countVisited())
}