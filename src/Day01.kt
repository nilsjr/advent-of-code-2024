fun main() {
    fun part1(input: List<String>): Int {
        val left = input
            .map { it.split("   ").first().toInt() }
            .sorted()
        val right = input
            .map { it.split("   ").last().toInt() }
            .sorted()

        return left
            .zip(right) { l, r ->
                if (l > r) l - r else r - l
            }
            .sum()
    }

    fun part2(input: List<String>): Int {
        val left = input
            .map { it.split("   ").first().toInt() }
        val right = input
            .map { it.split("   ").last().toInt() }

        return left.sumOf { number ->
            number * right.count { it == number }
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    println(part2(testInput))
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
