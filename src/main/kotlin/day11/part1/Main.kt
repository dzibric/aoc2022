package day11.part1

class Monkey(
    val startingItems: MutableList<Int> = mutableListOf(),
    val operation: (Int) -> (Int),
    val testDivisibleBy: Int = 1,
    val onTrueThrowTo: Int = 1,
    val onFalseThrowTo: Int = 1,
    var numberOfInspections: Int = 0
)

class Monkeys(val monkeys: MutableList<Monkey> = mutableListOf()) {
    fun simulateThrows() {
        monkeys.forEach { monkey ->
            val iterator = monkey.startingItems.iterator()
            while (iterator.hasNext()) {
                val item = iterator.next()
                val worryLevel = monkey.operation(item) / 3
                monkey.numberOfInspections++
                if (worryLevel % monkey.testDivisibleBy == 0) {
                    monkeys[monkey.onTrueThrowTo].startingItems.add(worryLevel)
                } else {
                    monkeys[monkey.onFalseThrowTo].startingItems.add(worryLevel)
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
    for (time in 0 until 20) {
        monkeys.simulateThrows()
        monkeys.printState()
    }
    var totalInspections = 1
    monkeys.monkeys.sortedByDescending { it.numberOfInspections }.take(2).forEach {
        totalInspections *= it.numberOfInspections
    }
    println(totalInspections)
}