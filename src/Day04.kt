fun main() {
    fun part1(input: List<String>): Int {
        val matrix = input.mapIndexed { x, string -> string.mapIndexed { y, ch -> Letter(x, y, ch.toString()) } }
        return matrix
            .map { letters -> letters.filter { it.value == "X" } }
            .flatten()
            .countWords(matrix)
    }

    fun part2(input: List<String>): Int {
        val matrix = input.mapIndexed { x, string -> string.mapIndexed { y, ch -> Letter(x, y, ch.toString()) } }
        return matrix
            .map { letters -> letters.filter { it.value == "A" } }
            .flatten()
            .countX(matrix)
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    val result = part2(testInput)
    println(result)
    check(result == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}

data class Letter(
    val x: Int,
    val y: Int,
    val value: String,
)

fun List<Letter>.countWords(input: List<List<Letter>>): Int {
    return this.map { s ->
        listOf(
            // up
            listOfNotNull(
                input.getOrNull(s.x)?.getOrNull(s.y - 1)?.takeIf { l -> l.value == "M" },
                input.getOrNull(s.x)?.getOrNull(s.y - 2)?.takeIf { l -> l.value == "A" },
                input.getOrNull(s.x)?.getOrNull(s.y - 3)?.takeIf { l -> l.value == "S" },
            ).size == 3,
            // tr
            listOfNotNull(
                input.getOrNull(s.x + 1)?.getOrNull(s.y - 1)?.takeIf { l -> l.value == "M" },
                input.getOrNull(s.x + 2)?.getOrNull(s.y - 2)?.takeIf { l -> l.value == "A" },
                input.getOrNull(s.x + 3)?.getOrNull(s.y - 3)?.takeIf { l -> l.value == "S" },
            ).size == 3,
            // right
            listOfNotNull(
                input.getOrNull(s.x + 1)?.getOrNull(s.y)?.takeIf { l -> l.value == "M" },
                input.getOrNull(s.x + 2)?.getOrNull(s.y)?.takeIf { l -> l.value == "A" },
                input.getOrNull(s.x + 3)?.getOrNull(s.y)?.takeIf { l -> l.value == "S" },
            ).size == 3,
            // br
            listOfNotNull(
                input.getOrNull(s.x + 1)?.getOrNull(s.y + 1)?.takeIf { l -> l.value == "M" },
                input.getOrNull(s.x + 2)?.getOrNull(s.y + 2)?.takeIf { l -> l.value == "A" },
                input.getOrNull(s.x + 3)?.getOrNull(s.y + 3)?.takeIf { l -> l.value == "S" },
            ).size == 3,
            // bottom
            listOfNotNull(
                input.getOrNull(s.x)?.getOrNull(s.y + 1)?.takeIf { l -> l.value == "M" },
                input.getOrNull(s.x)?.getOrNull(s.y + 2)?.takeIf { l -> l.value == "A" },
                input.getOrNull(s.x)?.getOrNull(s.y + 3)?.takeIf { l -> l.value == "S" },
            ).size == 3,
            // bl
            listOfNotNull(
                input.getOrNull(s.x - 1)?.getOrNull(s.y + 1)?.takeIf { l -> l.value == "M" },
                input.getOrNull(s.x - 2)?.getOrNull(s.y + 2)?.takeIf { l -> l.value == "A" },
                input.getOrNull(s.x - 3)?.getOrNull(s.y + 3)?.takeIf { l -> l.value == "S" },
            ).size == 3,
            // left
            listOfNotNull(
                input.getOrNull(s.x - 1)?.getOrNull(s.y)?.takeIf { l -> l.value == "M" },
                input.getOrNull(s.x - 2)?.getOrNull(s.y)?.takeIf { l -> l.value == "A" },
                input.getOrNull(s.x - 3)?.getOrNull(s.y)?.takeIf { l -> l.value == "S" },
            ).size == 3,
            // tl
            listOfNotNull(
                input.getOrNull(s.x - 1)?.getOrNull(s.y - 1)?.takeIf { l -> l.value == "M" },
                input.getOrNull(s.x - 2)?.getOrNull(s.y - 2)?.takeIf { l -> l.value == "A" },
                input.getOrNull(s.x - 3)?.getOrNull(s.y - 3)?.takeIf { l -> l.value == "S" },
            ).size == 3
        )
    }.flatten().count { bool -> bool }
}

fun List<Letter>.countX(input: List<List<Letter>>): Int {
    return this
        .map { s ->
            listOf(
                // /
                listOfNotNull(
                    input.getOrNull(s.x + 1)?.getOrNull(s.y - 1)?.takeIf { l -> l.value == "M" },
                    input.getOrNull(s.x - 1)?.getOrNull(s.y + 1)?.takeIf { l -> l.value == "S" },
                ).size == 2 || listOfNotNull(
                    input.getOrNull(s.x + 1)?.getOrNull(s.y - 1)?.takeIf { l -> l.value == "S" },
                    input.getOrNull(s.x - 1)?.getOrNull(s.y + 1)?.takeIf { l -> l.value == "M" },
                ).size == 2,
                // \
                listOfNotNull(
                    input.getOrNull(s.x - 1)?.getOrNull(s.y - 1)?.takeIf { l -> l.value == "M" },
                    input.getOrNull(s.x + 1)?.getOrNull(s.y + 1)?.takeIf { l -> l.value == "S" },
                ).size == 2 || listOfNotNull(
                    input.getOrNull(s.x - 1)?.getOrNull(s.y - 1)?.takeIf { l -> l.value == "S" },
                    input.getOrNull(s.x + 1)?.getOrNull(s.y + 1)?.takeIf { l -> l.value == "M" },
                ).size == 2,
            ).all { bool -> bool }
        }
        .count { bool -> bool }
}
