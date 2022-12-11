package day4.part1

import readFile

class Shift(val assignments: Pair<Assignment, Assignment>) {
    fun isCompletelyOverlapping(): Boolean {
        val isFirstCompletelyOverlapping =
            assignments.second.sections.first <= assignments.first.sections.first && assignments.second.sections.last >= assignments.first.sections.last
        val isSecondCompletelyOverlapping =
            assignments.second.sections.first >= assignments.first.sections.first && assignments.second.sections.last <= assignments.first.sections.last
        return isFirstCompletelyOverlapping or isSecondCompletelyOverlapping
    }
}

class Assignment(val sections: IntRange)

fun createAssignment(input: String): Assignment {
    val sections = input.split("-")
    return Assignment(sections[0].toInt()..sections[1].toInt())
}

fun main(args: Array<String>) {
    val input = readFile("src/day7.part2.day8.part2.day10.part2.day11.part2.main/kotlin/day4/part1/input.txt")
    println(input.map {
        val assignments = it.split(",")
        Shift(Pair(createAssignment(assignments[0]), createAssignment(assignments[1])))
    }.count { it.isCompletelyOverlapping() })
}