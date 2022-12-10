package day4.part2

import readFile

class Shift(val assignments: Pair<Assignment, Assignment>) {
    fun isOverlapping(): Boolean {
        return assignments.first.sections.intersect(assignments.second.sections).isNotEmpty()
    }
}

class Assignment(val sections: IntRange)

fun createAssignment(input: String): Assignment {
    val sections = input.split("-")
    return Assignment(sections[0].toInt()..sections[1].toInt())
}

fun main(args: Array<String>) {
    val input = readFile("src/day7.part2.day8.part2.day10.part2.main/kotlin/day4/part2/input.txt")
    println(input.map {
        val assignments = it.split(",")
        Shift(Pair(createAssignment(assignments[0]), createAssignment(assignments[1])))
    }.count { it.isOverlapping() })
}