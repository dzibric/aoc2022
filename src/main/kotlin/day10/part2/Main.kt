package day10.part2

import readFile
import java.security.InvalidParameterException

private const val PERIOD = 40
private const val CRT_HEIGHT = 6

abstract class Signal {
    abstract val cyclesToExecute: Int
    abstract val valueToChange: Int
}

class AddXSignal(override val valueToChange: Int) : Signal() {
    override val cyclesToExecute = 2
}

class NoopSignal: Signal() {
    override val cyclesToExecute = 1
    override val valueToChange = 0
}

class Cycle(val signal: Signal? = null)

class Sprite(var spritePosition: Int) {
    fun getSpriteRange() = IntRange(spritePosition, spritePosition + 2)
}

class RayTube(val signals: MutableList<Signal> = mutableListOf()) {
    val crtRender = MutableList(CRT_HEIGHT) { MutableList(PERIOD) { "." } }
    fun addSignal(signal: Signal) = signals.add(signal)

    fun readSignals(): MutableList<Cycle> {
        val sprite = Sprite(0)
        val cycles = mutableListOf<Cycle>()
        var period = PERIOD
        var crtLine = 0
        signals.forEach {
            when (it) {
                is NoopSignal -> cycles.add(Cycle(it))
                is AddXSignal -> {
                    cycles.add(Cycle(null))
                    cycles.add(Cycle(it))
                }
            }
        }
        cycles.forEachIndexed { index, cycle ->
            val drawSymbol = if (sprite.getSpriteRange().contains(index % PERIOD)) "#" else "."
            crtRender[crtLine][index % PERIOD] = drawSymbol
            sprite.spritePosition += cycle.signal?.valueToChange ?: 0
            if (period == index + 1) {
                period += PERIOD
                crtLine++
            }
        }
        printCrt(crtRender)
        return cycles
    }

    private fun printCrt(crt: MutableList<MutableList<String>>) {
        crt.forEach { row ->
            row.forEach { symbol ->
                print(symbol)
            }
            println()
        }
        println()
    }
}

fun main(args: Array<String>) {
    val input = readFile("src/day11.part2.main/kotlin/day10/input.txt")
    val rayTube = RayTube()
    input.forEach {
        val inputInfo = it.split(" ")
        when (inputInfo[0]) {
            "addx" -> rayTube.addSignal(AddXSignal(inputInfo[1].toInt()))
            "noop" -> rayTube.addSignal(NoopSignal())
            else -> throw InvalidParameterException("Not valid signal")
        }
    }
    rayTube.readSignals()
}