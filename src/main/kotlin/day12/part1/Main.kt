package day12.part1

import readFile
import java.util.*

// QItem for current location and distance
// from source location
internal class QItem(var row: Int, var col: Int, var dist: Int)
class Maze(val grid: Array<String> = arrayOf()) {
    fun minDistance(): Int {
        val source = QItem(0, 0, 0)

        // To keep track of visited QItems. Marking
        // blocked cells as visited.
        firstLoop@ for (i in grid.indices) {
            for (j in grid[i].indices) {

                // Finding source
                if (grid[i][j] == 'E') {
                    source.row = i
                    source.col = j
                    break@firstLoop
                }
            }
        }

        // applying BFS on matrix cells starting from source
        val queue: Queue<QItem> = LinkedList()
        queue.add(QItem(source.row, source.col, 0))
        val visited = Array(grid.size) { BooleanArray(grid[0].length) }
        visited[source.row][source.col] = true
        while (!queue.isEmpty()) {
            val p = queue.remove()

            // Destination found;
            val current = grid[p.row][p.col]
            if (current == 'a') {
                return p.dist
            }

            // moving up
            if (isValid(current, p.row - 1, p.col, grid, visited)) {
                queue.add(QItem(p.row - 1, p.col, p.dist + 1))
                visited[p.row - 1][p.col] = true
            }

            // moving down
            if (isValid(current, p.row + 1, p.col, grid, visited)) {
                queue.add(QItem(p.row + 1, p.col, p.dist + 1))
                visited[p.row + 1][p.col] = true
            }

            // moving left
            if (isValid(current, p.row, p.col - 1, grid, visited)) {
                queue.add(QItem(p.row, p.col - 1, p.dist + 1))
                visited[p.row][p.col - 1] = true
            }

            // moving right
            if (isValid(current, p.row, p.col + 1, grid, visited)) {
                queue.add(QItem(p.row, p.col + 1, p.dist + 1))
                visited[p.row][p.col + 1] = true
            }
        }
        return -1
    }

    // checking where it's valid or not
    private fun isValid(
        prev: Char,
        x: Int,
        y: Int,
        grid: Array<String>,
        visited: Array<BooleanArray>
    ): Boolean {
        return x >= 0 && y >= 0 && x < grid.size && y < grid[0].length && isOneStepUpOrSame(prev, grid[x][y]) && !visited[x][y]
    }

    private fun isOneStepUpOrSame(prev: Char, new: Char): Boolean {
        return when (prev) {
            'E' -> new == 'z'
            'b' -> new == 'a' || new == 'S' || new == 'b'
            else -> prev.code - new.code <= 1
        }
    }
}

fun main(args: Array<String>) {
    val input = readFile("src/day13.part1.day12.part1.main/kotlin/day12/input.txt")
    val maze = Maze(input.toTypedArray())
    println(maze.minDistance())
}