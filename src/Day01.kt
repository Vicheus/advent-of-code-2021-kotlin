import kotlin.math.ceil

fun main() {
    fun part1(input: List<String>): Int {
        return input.drop(1).foldIndexed(0) { i: Int, acc: Int, el: String -> acc + ceil((el.toDouble() / input[i].toDouble()) - 1.0).toInt() }
    }

    fun part2(input: List<String>): Int {
        val windowedInput = input.windowed(size = 3, step = 1)
        return windowedInput.drop(1).foldIndexed(0) { i: Int, acc: Int, el: List<String> ->
            val first = windowedInput[i].sumOf { it.toInt() }.toDouble()
            val second = el.sumOf { it.toInt() }.toDouble()
            val increment = ceil((second / first) - 1).toInt()
            acc + increment
        }
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
