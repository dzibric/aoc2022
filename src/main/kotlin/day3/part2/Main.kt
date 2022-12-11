package day3.part2

import day3.part1.Rucksack
import readFile
import java.lang.IllegalArgumentException

data class Group(val items: Triple<String, String, String>) {
    fun getIntersectingElementsSum(): Int {
        return items.first.toCharArray().toList().intersect(items.second.toCharArray().toList().toSet())
            .intersect(items.third.toCharArray().toList().toSet()).sumOf {
            if (it.isUpperCase()) {
                it.code - 38
            } else if (it.isLowerCase()) {
                it.code - 96
            } else throw IllegalArgumentException("Should not have special characters or numbers")
        }
    }
}

fun createGroups(items: List<String>): List<Group> {
    return items.chunked(3).map { Group(Triple(it[0], it[1], it[2])) }
}


fun main(args: Array<String>) {
    val input = readFile("src/day7.part2.day8.part2.day10.part2.day11.part2.main/kotlin/day3/part2/input.txt")
    println(createGroups(input).sumOf { it.getIntersectingElementsSum() })
}