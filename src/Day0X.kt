fun main() {
    fun part1(input: List<String>): Int {

        return 0
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    println(part2(testInput))
    check(part2(testInput) == 0)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
