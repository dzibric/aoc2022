package day5.part2

import readFile
import java.util.ArrayDeque
import kotlin.math.max

data class Ship(val stacks: List<ArrayDeque<Char>>) {
    fun showShip() {
        val maxColumn = stacks.maxOf { it.size }
        for (row in maxColumn downTo  0) {
            for (column in stacks.indices) {
                if (row < stacks[column].size) {
                    print("[" + stacks[column].toList()[stacks[column].size - 1 - row] + "]")
                } else {
                    print("   ")
                }
            }
            println()
        }
    }
    fun moveCrates(from: Int, to: Int, number: Int) {
        val crates = stacks[from - 1].take(number).reversed()
        for (step in 0 until number) {
            stacks[from - 1].pop()
            stacks[to - 1].push(crates[step])
        }
    }
}

fun initializeShip(): Ship {
    return Ship(listOf(
        ArrayDeque(listOf('M', 'F', 'C', 'W', 'T', 'D', 'L', 'B')),
        ArrayDeque(listOf('L', 'B', 'N')),
        ArrayDeque(listOf('V', 'L', 'T', 'H', 'C', 'J')),
        ArrayDeque(listOf('W', 'J', 'P', 'S')),
        ArrayDeque(listOf('R', 'L', 'T', 'F', 'C', 'S', 'Z')),
        ArrayDeque(listOf('Z', 'N', 'H', 'B', 'G', 'D', 'W')),
        ArrayDeque(listOf('N', 'C', 'G', 'V', 'P', 'S', 'M', 'F')),
        ArrayDeque(listOf('Z', 'C', 'V', 'F', 'J', 'R', 'Q', 'W')),
        ArrayDeque(listOf('H', 'L', 'M', 'P', 'R'))
    ))
}

fun getStepInfo(input: String): Triple<Int, Int, Int> {
    val numbers = Regex("[0-9]+").findAll(input)
        .map(MatchResult::value)
        .toList()
    return Triple(numbers[0].toInt(), numbers[1].toInt(), numbers[2].toInt())
}

fun main(args: Array<String>) {
    val input = readFile("src/day7.part2.day8.part2.day10.part2.day11.part2.day13.part1.day12.part1.main/kotlin/day5/part2/input.txt")
    val ship = initializeShip()
    input.forEach {
        val info = getStepInfo(it)
        ship.moveCrates(info.second, info.third, info.first)
    }
    ship.showShip()
}