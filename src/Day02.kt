import java.lang.IllegalArgumentException

fun main() {
    fun part1(input: List<String>): Int {
        return input.fold(mutableMapOf("forward" to 0, "down" to 0, "up" to 0)) { acc, el ->
            val (movement, value) = el.split(" ")
            acc[movement] = acc[movement]?.plus(value.toInt()) ?: 0
            acc
        }.let { movements -> (movements["down"]!! - movements["up"]!!) * movements["forward"]!! }
    }

    fun part2(input: List<String>): Int {
        data class Movement(
            val horizontal: Int,
            val depth: Int,
            val aim: Int,
        ) {
            fun calculate(): Int = horizontal * depth
            fun forward(value: Int) = copy(horizontal = horizontal + value, depth = depth + value * aim)
            fun down(value: Int) = copy(aim = aim + value)
            fun up(value: Int) = copy(aim = aim - value)
        }
        return input.fold(Movement(0, 0, 0)) { acc, el ->
            val (movement, value) = el.split(" ")
            when (movement) {
                "forward" -> acc.forward(value.toInt())
                "down" -> acc.down(value.toInt())
                "up" -> acc.up(value.toInt())
                else -> throw IllegalArgumentException("Unexpected movement $movement")
            }
        }.also { println(it) }.calculate()
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
