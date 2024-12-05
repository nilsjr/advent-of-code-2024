import kotlin.collections.map

fun main() {
    fun part1(input: List<String>): Int {
        val rules = input.takeWhile { string -> string.isNotEmpty() }
            .map {
                val i = it.split("|").map { string -> string.toInt() }
                i.first() to i[1]
            }
        val updates = input
            .takeLastWhile { string -> string.isNotEmpty() }
            .map { string -> string.split(",").map { it.toInt() } }

        return updates
            .filter { l -> isValid(l, rules) }
            .sumOf { i ->
                i[i.size / 2]
            }
    }

    fun part2(input: List<String>): Int {
        val rules = input.takeWhile { string -> string.isNotEmpty() }
            .map {
                val i = it.split("|").map { string -> string.toInt() }
                i.first() to i[1]
            }
        val updates = input
            .takeLastWhile { string -> string.isNotEmpty() }
            .map { string -> string.split(",").map { it.toInt() } }

        val invalid = updates.filter { l -> !isValid(l, rules) }

        invalid.println()

        val c = invalid.map { ints -> reorder(ints, rules) }

        c.println()

        return c.sumOf { i ->
            i[i.size / 2]
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    val result = part2(testInput)
    println(result)
    check(result == 123)

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}

fun reorder(input: List<Int>, rules: List<Pair<Int, Int>>): List<Int> {
    var start = input.toMutableList()

    while (!isValid(start, rules)) {
        val m = start
            .mapIndexed { index, page ->
                ((index + 1)..start.lastIndex).map { i -> page to start[i] }
            }
            .filter { pairs -> pairs.isNotEmpty() }
            .map { pairs ->
                pairs.filter { pair ->
                    rules.contains(pair.second to pair.first)
                }
            }
            .filter { pairs -> pairs.isNotEmpty() }

        m.forEach { t ->
            t.forEach { pair ->
                val x = start.indexOf(pair.first)
                val y = start.indexOf(pair.second)
                start.swap(x, y)
            }
        }
    }

    return start
}

fun isValid(line: List<Int>, rules: List<Pair<Int, Int>>): Boolean {
    val m = line
        .mapIndexed { index, page ->
            ((index + 1)..line.lastIndex).map { i -> page to line[i] }
        }
        .filter { pairs -> pairs.isNotEmpty() }

    return m.all { pairs ->
        pairs.all { pair ->
            rules.contains(pair) && !rules.contains(pair.second to pair.first)
        }
    }
}

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}