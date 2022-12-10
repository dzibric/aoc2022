package day6.part1

import readFile

fun checkSignal(string: String): Int {
    for (index in string.indices) {
        if (index + 3 < string.length) {
            val substring = string.substring(index, index + 4)
            val distinctList = substring.toCharArray().toList().distinct()
            if (distinctList.size == substring.length) {
                return index + 4
            }
        }
    }
    return 0
}
fun main(args: Array<String>) {
    val input = readFile("src/day8.part2.day10.part2.main/kotlin/day6/input.txt")
    println(checkSignal(input[0]))
}