package day10.part1

import readFile
import java.security.InvalidParameterException

private const val PERIOD = 40

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

class RayTube(val signals: MutableList<Signal> = mutableListOf()) {
    fun addSignal(signal: Signal) = signals.add(signal)

    fun readSignals(): Pair<MutableList<Cycle>, Int> {
        var xValue = 1
        val cycles = mutableListOf<Cycle>()
        var period = PERIOD
        var strength = 0
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
            if (period == index + 1) {
                strength += xValue * period
                period += PERIOD
            }
            xValue += cycle.signal?.valueToChange ?: 0
        }
        return Pair(cycles, strength)
    }
}

fun main(args: Array<String>) {
    val input = readFile("src/main/kotlin/day10/input.txt")
    val rayTube = RayTube()
    input.forEach {
        val inputInfo = it.split(" ")
        when (inputInfo[0]) {
            "addx" -> rayTube.addSignal(AddXSignal(inputInfo[1].toInt()))
            "noop" -> rayTube.addSignal(NoopSignal())
            else -> throw InvalidParameterException("Not valid signal")
        }
    }
    val results = rayTube.readSignals()
    results.first.forEachIndexed { index, cycle ->
        println(index.toString() + ", " + cycle.signal?.valueToChange)
    }
    println(results.second)
}