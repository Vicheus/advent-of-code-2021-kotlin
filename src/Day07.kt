import kotlin.math.abs

fun main() {
    fun List<String>.toInts() = first().split(",").map { it.toInt() }

    fun part1(input: List<String>): Int {
        val inputInts = input.toInts()
        val orderedInts = List(inputInts.maxOf { it } + 1) { it }
        return orderedInts.map { int -> inputInts.sumOf { abs(it - int) } }.minOf { it }
    }

    fun calculateFuel(n: Int, s: Int = 0): Int {
        if (n == 0) return s
        return calculateFuel(s = s + n, n = n - 1)
    }

    fun part2(input: List<String>): Int {
        val inp = input.first().split(",").map { it.toInt() }
        val ints = List(inp.maxOf { it } + 1) { it }
        return ints.map { int -> inp.sumOf { calculateFuel(abs(it - int)) } }.minOf { it }
    }

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

