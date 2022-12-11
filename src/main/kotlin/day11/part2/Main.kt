package day11.part2

import productOf

class Monkey(
    val startingItems: MutableList<Long> = mutableListOf(),
    val operation: (Long) -> (Long),
    val testDivisibleBy: Long = 1,
    val onTrueThrowTo: Long = 1,
    val onFalseThrowTo: Long = 1,
    var numberOfInspections: Long = 0
)

class Monkeys(val monkeys: MutableList<Monkey> = mutableListOf()) {
    fun simulateThrows(mod: Long) {
        monkeys.forEach { monkey ->
            val iterator = monkey.startingItems.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                val worryLevel = monkey.operation(item) % mod
                monkey.numberOfInspections++
                if (worryLevel % monkey.testDivisibleBy == 0L) {
                    monkeys[monkey.onTrueThrowTo.toInt()].startingItems.add(worryLevel)
                } else {
                    monkeys[monkey.onFalseThrowTo.toInt()].startingItems.add(worryLevel)
                }
                iterator.remove()
            }
        }
    }

    fun printState() {
        monkeys.forEachIndexed { index, monkey ->
            print("Monkey $index:")
            monkey.startingItems.forEach {
                print("$it ")
            }
            println()
            println("Inspections: " + monkey.numberOfInspections)
        }
    }
}

fun main(args: Array<String>) {
    val monkeys = Monkeys(
        mutableListOf(
            Monkey(
                startingItems = mutableListOf(93, 98),
                operation = {
                    it * 17
                },
                testDivisibleBy = 19,
                onTrueThrowTo = 5,
                onFalseThrowTo = 3
            ),
            Monkey(
                startingItems = mutableListOf(95, 72, 98, 82, 86),
                operation = {
                    it + 5
                },
                testDivisibleBy = 13,
                onTrueThrowTo = 7,
                onFalseThrowTo = 6
            ),
            Monkey(
                startingItems = mutableListOf(85, 62, 82, 86, 70, 65, 83, 76),
                operation = {
                    it + 8
                },
                testDivisibleBy = 5,
                onTrueThrowTo = 3,
                onFalseThrowTo = 0
            ),
            Monkey(
                startingItems = mutableListOf(86, 70, 71, 56),
                operation = {
                    it + 1
                },
                testDivisibleBy = 7,
                onTrueThrowTo = 4,
                onFalseThrowTo = 5
            ),
            Monkey(
                startingItems = mutableListOf(77, 71, 86, 52, 81, 67),
                operation = {
                    it + 4
                },
                testDivisibleBy = 17,
                onTrueThrowTo = 1,
                onFalseThrowTo = 6
            ),
            Monkey(
                startingItems = mutableListOf(89, 87, 60, 78, 54, 77, 98),
                operation = {
                    it * 7
                },
                testDivisibleBy = 2,
                onTrueThrowTo = 1,
                onFalseThrowTo = 4
            ),
            Monkey(
                startingItems = mutableListOf(69, 65, 63),
                operation = {
                    it + 6
                },
                testDivisibleBy = 3,
                onTrueThrowTo = 7,
                onFalseThrowTo = 2
            ),
            Monkey(
                startingItems = mutableListOf(89),
                operation = {
                    it * it
                },
                testDivisibleBy = 11,
                onTrueThrowTo = 0,
                onFalseThrowTo = 2
            )
        )
    )
    val mod = monkeys.monkeys.productOf { it.testDivisibleBy.toInt() }.toLong()
    for (time in 0 until 10000) {
        monkeys.simulateThrows(mod)
        monkeys.printState()
    }
    var totalInspections = 1L
    monkeys.monkeys.sortedByDescending { it.numberOfInspections }.take(2).forEach {
        totalInspections *= it.numberOfInspections
    }
    println(totalInspections)
}