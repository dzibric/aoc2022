package day7.part1

import readFile
import java.util.ArrayDeque

lateinit var currentDirectory: Directory

var listingInProgress: Boolean = false

fun decodeLine(string: String) {
    if (string.contains("\$ cd")) {
        executeCommand(Command.CHANGE_DIR, listOf(string.substringAfterLast(" ")))
    } else if (string.contains("\$ ls")) {
        executeCommand(Command.LIST_DIR)
    } else if (string.startsWith("dir")) {
        val dirName = string.substringAfterLast(" ")
        if (listingInProgress && !currentDirectory.hasDirectory(dirName)) {
            currentDirectory.addDirectory(Directory(dirName, parentDirectory = currentDirectory))
        }
    } else if (string[0].isDigit()) {
        val fileInfo = string.split(" ")
        val fileSize = fileInfo[0]
        val fileName = fileInfo[1]
        val file = File(fileName, fileSize.toLong())
        if (!currentDirectory.hasFile(fileName)) {
            currentDirectory.addFile(file)
        }
    }
}

fun executeCommand(command: Command, params: List<String> = emptyList()) {
    when (command) {
        Command.CHANGE_DIR -> {
            listingInProgress = false
            currentDirectory = when (val dir = params[0].substringAfterLast(" ")) {
                "/" -> {
                    Directory(dir)
                }
                ".." -> {
                    currentDirectory.parentDirectory!!
                }
                else -> {
                    val directory = currentDirectory.getDirectory(dir)
                    if (directory == null) {
                        currentDirectory.directories.add(Directory(dir, parentDirectory = currentDirectory))
                    }
                    currentDirectory.getDirectory(dir)!!
                }
            }
        }
        Command.LIST_DIR -> {
            listingInProgress = true
        }
    }
}

var newSum = 0L

class Directory(
    val name: String,
    val directories: MutableList<Directory> = mutableListOf(),
    val files: MutableList<File> = mutableListOf(),
    val parentDirectory: Directory? = null
) {
    fun addDirectory(directory: Directory) = directories.add(directory)
    fun addFile(file: File) = files.add(file)

    fun getDirectory(name: String) = directories.find { it.name == name }
    fun hasDirectory(name: String) = directories.any { it.name == name }

    fun getFile(name: String) = files.find { it.fileName == name }
    fun hasFile(name: String) = files.any { it.fileName == name }

    fun printDirectoryDetails() {
        if (parentDirectory != null) {
            parentDirectory.printDirectoryDetails()
        } else {
            printDetails()
        }
    }

    fun printDetails(level: Int = 1) {
        print("Directory: $name, ${getSize()}")
        println()
        directories.forEach {
            print(" ".repeat(level))
            it.printDetails(level + 1)
        }
        files.forEach {
            print(" ".repeat(level))
            it.printFileDetails()
        }
    }

    fun printNameAndSize() {
        println("Directory: $name, ${getSize()}")
    }

    fun getSize(): Long {
        val filesSum = files.sumOf { it.fileSize }
        val directoriesSum = directories.sumOf { directory -> directory.getSize() }
        return filesSum + directoriesSum
    }

    fun getTopDirectory(): Directory {
        return parentDirectory ?: getTopDirectory()
    }

    fun getSumOfAll(): Long {
        return parentDirectory?.getSumOfAll() ?: getSum()
    }

    fun getSum(): Long {
        println(newSum)
        directories.forEach {
            val size = it.getSize()
            newSum = if (size <= 100000) {
                newSum + size
            } else newSum
            it.getSum()
        }
        return newSum
    }
}

class File(val fileName: String, val fileSize: Long) {
    fun printFileDetails() = println("File: [$fileName ,$fileSize] ")
}

enum class Command {
    CHANGE_DIR,
    LIST_DIR
}

fun main(args: Array<String>) {
    val input = readFile("src/day8.part2.day10.part2.main/kotlin/day7/input.txt")
    input.forEach {
        decodeLine(it)
    }
    newSum = 0
    currentDirectory.getSumOfAll()
}