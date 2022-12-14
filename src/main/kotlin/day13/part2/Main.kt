package day13.part2

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
    val packets = mutableListOf<Packet>()
    input.forEach {
        if (it.isNotBlank()) {
            packets.add(generatePacket(it))
        }
    }
    val additionalPacket1 = generatePacket("[[2]]")
    val additionalPacket2 = generatePacket("[[6]]")
    var result = 1
    val sortedListOfPackets = packets.sortedWith { p0, pi -> if (isInRightOrder(Pair(p0, pi)).second) -1 else 1 }.toMutableList()
    var index = 0
    while (index < sortedListOfPackets.size) {
        val prevPacket = if (index - 1 < 0) additionalPacket1 else sortedListOfPackets[index - 1]
        val currPacket = sortedListOfPackets[index]
        if (isInRightOrder(Pair(additionalPacket1, currPacket)).second && isInRightOrder(Pair(prevPacket, additionalPacket1)).second) {
            result *= index + 1
            sortedListOfPackets.add(index, additionalPacket1)
            break
        }
        index++
    }
    while (index < sortedListOfPackets.size) {
        val prevPacket = if (index - 1 < 0) additionalPacket2 else sortedListOfPackets[index - 1]
        val currPacket = sortedListOfPackets[index]
        if (isInRightOrder(Pair(additionalPacket2, currPacket)).second && isInRightOrder(Pair(prevPacket, additionalPacket2)).second) {
            result *= index + 1
            sortedListOfPackets.add(index, additionalPacket2)
            break
        }
        index++
    }
    sortedListOfPackets.forEach { packet -> packet.printPacket() }
    println(result)
}