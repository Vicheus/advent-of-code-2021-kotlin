
fun main() {
    val initMap = { mutableMapOf(
        8 to 0L,
        7 to 0L,
        6 to 0L,
        5 to 0L,
        4 to 0L,
        3 to 0L,
        2 to 0L,
        1 to 0L,
        0 to 0L,
    ) }

    fun MutableMap<Int, Long>.calculate(day: Int, stopAt: Int): MutableMap<Int, Long> {
        if (day == stopAt) return this
        val newCounter = initMap()
        this.keys.toList().dropLast(1).forEach { key -> newCounter[key - 1] = this[key]!! }
        val zero = this.getOrDefault(0, 0L)
        newCounter[6] = newCounter.getOrDefault(6, 0L) + zero
        newCounter[8] = zero
        return newCounter.calculate(day = day + 1, stopAt = stopAt)
    }

    fun List<String>.initCounter() = first().split(",")
        .fold(initMap()) { acc, s ->
            acc[s.toInt()] = acc.getOrDefault(s.toInt(), 0L) + 1L
            acc
        }

    fun part1(input: List<String>): Long {
        return input.initCounter()
            .calculate(day = 0, stopAt = 80).values.sum()
    }

    fun part2(input: List<String>): Long {
        return input.initCounter()
            .calculate(day = 0, stopAt = 256).values.sum()
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

