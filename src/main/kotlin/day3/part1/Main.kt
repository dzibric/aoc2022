package day3.part1

import readFile
import java.lang.IllegalArgumentException

data class Rucksack(val compartments: Pair<String, String>) {
    fun getIntersectingElementsSum(): Int {
        return compartments.first.toCharArray().toList().intersect(compartments.second.toCharArray().toList().toSet()).sumOf {
            if (it.isUpperCase()) {
                it.code - 38
            } else if (it.isLowerCase()) {
                it.code - 96
            } else throw IllegalArgumentException("Should not have special characters or numbers")
        }
    }
}

fun createRucksack(items: String): Rucksack {
    val half = items.length / 2
    return Rucksack(Pair(items.slice(0 until half), items.slice(half until items.length)))
}


fun main(args: Array<String>) {
    val input = readFile("src/day7.part2.day8.part2.day10.part2.day11.part2.main/kotlin/day3/part1/input.txt")
    println(input.sumOf { createRucksack(it).getIntersectingElementsSum() })
}