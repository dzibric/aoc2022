package day13.part1

import readFile
import java.lang.Exception
import java.util.*

abstract class PacketContent

class Data(val number: Int): PacketContent()

class Packet(val content: List<PacketContent> = mutableListOf(), val level: Int = 0, val contentString: String? = null): PacketContent() {
    fun printPacket() {
        println(contentString)
        return
    }
}

fun generatePacket(input: String, level: Int = 0): Packet {
    val packetContent = mutableListOf<PacketContent>()
    var number = ""
    val substringInput = input.substring(if (level == 0) 1 else 0, input.length)
    var index = 0
    var contentString = if (level == 0) "[" else ""
    while (index < substringInput.length) {
        val char = substringInput[index]
        contentString += char
        if (char == '[') {
            val packet = generatePacket(substringInput.substring(index + 1, substringInput.length), level + 1)
            index += packet.contentString?.length ?: 0
            contentString += packet.contentString
            packetContent.add(packet)
        } else if (char.isDigit()) {
            number += char
        } else if (char == ',' && number != "") {
            packetContent.add(Data(number.toInt()))
            number = ""
        } else if (char == ']') {
            if (number != "") {
                packetContent.add(Data(number.toInt()))
            }
            return Packet(packetContent, level, contentString)
        }
        index++
    }
    return Packet()
}

fun isInRightOrder(packetPair: Pair<Packet, Packet>): Pair<Boolean, Boolean> {
    var index = 0
    while (true) {
        val left = packetPair.first
        val right = packetPair.second
        when {
            index >= left.content.size && index >= right.content.size -> return Pair(true, true)
            index >= left.content.size && index < right.content.size -> return Pair(false, true)
            index < left.content.size && index >= right.content.size -> return Pair(false, false)
        }
        val leftElement = left.content[index]
        val rightElement = right.content[index]
        if (leftElement is Data && rightElement is Data) {
            if (leftElement.number == rightElement.number) {
                index++
                continue
            } else {
                return Pair(false, leftElement.number < rightElement.number)
            }
        } else if (leftElement is Packet && rightElement is Packet) {
            val result = isInRightOrder(Pair(leftElement, rightElement))
            if (!result.first) return result
        } else if (leftElement is Data && rightElement is Packet) {
            val result = isInRightOrder(Pair(Packet(content = listOf(leftElement)), rightElement))
            if (!result.first) return result
        } else if (leftElement is Packet && rightElement is Data) {
            val result = isInRightOrder(Pair(leftElement, Packet(content = listOf(rightElement))))
            if (!result.first) return result
        }
        index++
    }
}

fun main(args: Array<String>) {
    val input = readFile("src/main/kotlin/day13/input.txt")
    val packetPairs = mutableListOf<Pair<Packet, Packet>>()
    input.forEachIndexed { index, _ ->
        if (index + 1 == input.size || input[index + 1].isEmpty()) {
            packetPairs.add(Pair(generatePacket(input[index - 1]), generatePacket(input[index])))
        }
    }
    var result = 0
    packetPairs.forEachIndexed { index, pair ->
        pair.first.printPacket()
        pair.second.printPacket()
        val isRightOrder = isInRightOrder(pair)
        if (isRightOrder.second) {
            result += index + 1
        }
        println(isRightOrder.second)
        println()
    }
    println(result)
}