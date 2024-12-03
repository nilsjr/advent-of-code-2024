import kotlin.collections.flatten

fun main() {
    fun part1(input: List<String>): Int {
        val validMulList = input.map {
            Regex("mul\\(\\d{1,3},\\d{1,3}\\)").findAll(it)
                .map { result ->
                    val (a, b) = result.value.drop(4).dropLast(1).split(",")
                    a.toInt() to b.toInt()
                }
                .toList()
        }.flatten()

        return validMulList.sumOf { (a, b) ->
            a * b
        }
    }

    fun part2(input: List<String>): Int {
        val validMulList = input.map {
            Regex("mul\\(\\d{1,3},\\d{1,3}\\)|don't\\(\\)|do\\(\\)").findAll(it)
                .map { result -> result.value }
                .toList()
        }.flatten().mapNotNull { result -> result.toInstruction() }

        val result = validMulList
            .fold(Result(emptyList(), true)) { result, next ->
                when(next) {
                    Instruction.Do -> result.copy(enabled = true)
                    Instruction.Dont -> result.copy(enabled = false)
                    is Instruction.Mul -> {
                        if(result.enabled) {
                            result.copy(list = result.list + next)
                        } else {
                            result
                        }
                    }
                }
            }

        return result.list.sumOf { it.a * it.b }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    val result = part2(testInput)
    println(result)
    check(result == 48)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}

data class Result(
    val list: List<Instruction.Mul>,
    val enabled: Boolean,
)

sealed interface Instruction {

    data object Dont : Instruction
    data object Do : Instruction
    data class Mul(val a: Int, val b: Int) : Instruction
}

fun String.toInstruction(): Instruction? {
    return when {
        this.startsWith("mul(") -> {
            val (a, b) = this.drop(4).dropLast(1).split(",")
            Instruction.Mul(a.toInt(), b.toInt())
        }

        this == "don't()" -> {
            Instruction.Dont
        }

        this == "do()" -> {
            Instruction.Do
        }

        else -> null
    }
}
