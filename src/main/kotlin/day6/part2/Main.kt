package day6.part2

import readFile

fun checkSignal(string: String): Int {
    for (index in string.indices) {
        if (index + 14 < string.length) {
            val substring = string.substring(index, index + 14)
            val distinctList = substring.toCharArray().toList().distinct()
            if (distinctList.size == substring.length) {
                return index + 14
            }
        }
    }
    return 0
}
fun main(args: Array<String>) {
    val input = readFile("src/day8.part2.day10.part2.main/kotlin/day6/input.txt")
    println(checkSignal(input[0]))
}