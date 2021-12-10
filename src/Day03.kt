import kotlin.math.round

fun main() {
    fun List<String>.findMostCommonBit(position: Int): Int {
        return fold(0) {acc, row ->
            acc + row[position].toString().toInt()
        }
            .let { if (it.toDouble() == size.toDouble() / 2) return 1 else it }
            .let { round(it.toDouble() / size).toInt() }
    }

    fun String.invertBinaryNumber(): String = map { c -> c.toString().toInt() xor 1 }.toList().joinToString("")

    fun String.calculatePowerConsumption() = let {
        val gammaRate = it.toInt(2)
        val epsilonRate = it.invertBinaryNumber().toInt(2)
        gammaRate * epsilonRate
    }

    fun part1(input: List<String>): Int {
        return input[0].foldIndexed(listOf<Int>()) { i, acc, _ ->
            acc + input.findMostCommonBit(i)
        }
            .joinToString("")
            .calculatePowerConsumption()
    }

    fun List<String>.filter(position: Int, invert: Boolean = false): List<String> {
        if (this.size == 1) return this
        val newPosition = position + 1 % this[0].length
        val predicate = findMostCommonBit(position).let { if (invert) it xor 1 else it }
        return filter {
            val filterableElement = it[position].toString().toInt()
            filterableElement == predicate
        }
            .filter(newPosition, invert)
    }

    fun part2(input: List<String>): Int {
        val oxRating = input.filter(0).single().toInt(2)
        val co2Rating = input.filter(0, true).single().toInt(2)
        return oxRating * co2Rating
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

