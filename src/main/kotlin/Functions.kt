import java.io.File
import java.io.InputStream

fun readFile(fileName: String): List<String> {
    val inputStream: InputStream = File(fileName).inputStream()
    val lineList = mutableListOf<String>()

    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    return lineList
}

fun String.print() {
    println(this)
}

fun List<String>.print() {
    this.forEach {
        it.print()
    }
}

inline fun <T> Iterable<T>.productOf(selector: (T) -> Int): Int {
    var sum = 1
    for (element in this) {
        sum *= selector(element)
    }
    return sum
}

fun <T>List<List<T>>.getElement(x: Int, y: Int): T {
    val xIndex = when {
        x >= size -> x - size
        x < 0 -> x + size
        else -> x
    }
    val yIndex = when {
        y >= this[0].size -> y - this[0].size
        y < 0 -> y + this[0].size
        else -> y
    }
    return this[xIndex][yIndex]
}

fun <T>ArrayList<ArrayList<T>>.putElement(x: Int, y: Int, element: T) {
    val xIndex = when {
        x >= size -> x - size
        x < 0 -> x + size
        else -> x
    }
    val yIndex = when {
        y >= this[0].size -> y - this[0].size
        y < 0 -> y + this[0].size
        else -> y
    }
    this[xIndex][yIndex] = element
}