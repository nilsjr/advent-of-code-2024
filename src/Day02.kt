import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { line -> line.split(" ").map { it.toLong() } }
            .count { report ->
                val isAsc = report.checkLevel { a, b ->
                    a < b && abs(b - a) <= 3
                }
                val isDesc = report.checkLevel { a, b ->
                    a > b && abs(a - b) <= 3
                }
                isAsc || isDesc
            }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { line -> line.split(" ").map { it.toLong() } }
            .count { report ->
                report.checkLevelWithDampener { a, b -> a - b in (1..3) } ||
                        report.checkLevelWithDampener { a, b -> b - a in (1..3) }
            }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    val result = part2(testInput)
    println(result)
    check(result == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

private fun List<Long>.checkLevel(block: (Long, Long) -> Boolean): Boolean = this
    .asSequence()
    .zipWithNext { a, b -> block(a, b) }
    .all { it }

private fun List<Long>.checkLevelWithDampener(block: (Long, Long) -> Boolean): Boolean = this.indices.any { index ->
    this.toMutableList()
        .apply { removeAt(index) }
        .asSequence()
        .zipWithNext { a, b -> block(a, b) }
        .all { it }
}
