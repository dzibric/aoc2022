package day7.part2

import day7.part1.Directory
import day7.part1.currentDirectory
import day7.part1.decodeLine
import day7.part1.newSum
import readFile

private const val AVAILABLE_SPACE = 70000000
private const val UPDATE_SPACE_NEEDED = 30000000

private var topDirectorySize = 0L

var allDirectoriesAvailableForDeleting = mutableListOf<Directory>()

fun directoriesAvailableForDeleting(directory: Directory, size: Long) {
    if (AVAILABLE_SPACE - UPDATE_SPACE_NEEDED > size - directory.getSize()) {
        allDirectoriesAvailableForDeleting.add(directory)
    }
    directory.directories.forEach {
        directoriesAvailableForDeleting(it, size)
    }
}

fun main(args: Array<String>) {
    val input = readFile("src/day8.part2.day10.part2.main/kotlin/day7/input.txt")
    input.forEach {
        decodeLine(it)
    }
    val topDirectory = currentDirectory.getTopDirectory()
    topDirectorySize = topDirectory.getSize()
    directoriesAvailableForDeleting(topDirectory, topDirectorySize)
    allDirectoriesAvailableForDeleting.forEach { it.printNameAndSize() }
    println(topDirectorySize)
    println(allDirectoriesAvailableForDeleting.minOf { it.getSize() })
}