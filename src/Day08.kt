
fun main() {
    fun part1(input: List<String>): Int {
        val numberToSegment = mapOf(
            6 to "0",
            2 to "1",
            5 to "2",
            5 to "3",
            4 to "4",
            5 to "5",
            6 to "6",
            3 to "7",
            7 to "8",
            6 to "9",
        )
        val matches = mutableMapOf(
            "0" to 0,
            "1" to 0,
            "2" to 0,
            "3" to 0,
            "4" to 0,
            "5" to 0,
            "6" to 0,
            "7" to 0,
            "8" to 0,
            "9" to 0,
        )
        val numbers = listOf("1", "4", "7", "8")
        return input.fold(matches) { acc, s ->
            s.split("| ").last().split(" ").forEach { e ->
                val number = numberToSegment[e.length]!!
                acc[number] = acc[number]!! + 1
            }
            acc
        }
            .filter { it.key in numbers }
            .values.sum()
    }

    fun part2(input: List<String>): Int {
        data class Num(
            val name: String,
            val size: Int,
            var chars: Set<Char> = setOf(),
        )

        class Display(sequence: String) {
            private val zero = Num(name = "0", size = 6)
            private val one = Num(name = "1", size = 2)
            private val two = Num(name = "2", size = 5)
            private val three = Num(name = "3", size = 5)
            private val four = Num(name = "4", size = 4)
            private val five = Num(name = "5", size = 5)
            private val six = Num(name = "6", size = 6)
            private val seven = Num(name = "7", size = 3)
            private val eight = Num(name = "8", size = 7)
            private val nine = Num(name = "9", size = 6)

            private val numbers = listOf(zero, one, two, three, four, five, six, seven, eight, nine)

            private var patterns: List<Set<Char>> = sequence.split(" | ").first().split(" ").map { it.toSet() }
            private var output: List<Set<Char>> = sequence.split(" | ").last().split(" ").map { it.toSet() }

            private lateinit var charsToNum: Map<Set<Char>, Num>

            fun detectNumbers(): Display {
                patterns.mapNotNull { chars ->
                    val num = detectNumber(chars) ?: return@mapNotNull chars
                    num.chars = chars
                    null
                }.forEach { chars ->
                    val num = detectNumber(chars) ?: return@forEach
                    num.chars = chars
                }
                charsToNum = this.numbers.associateBy { it.chars }

                return this
            }

            fun decodeOutput(): Int {
                return output.joinToString("") { charsToNum[it]?.name ?: "0" }.toInt()
            }

            private fun detectNumber(chars: Set<Char>): Num? {
                return isOne(chars)
                    ?: isFour(chars)
                    ?: isSeven(chars)
                    ?: isEight(chars)
                    ?: isZero(chars)
                    ?: isTwo(chars)
                    ?: isThree(chars)
                    ?: isFive(chars)
                    ?: isSix(chars)
                    ?: isNine(chars)
            }

            private fun isZero(chars: Set<Char>): Num? {
                if (chars.size != zero.size) return null

                val match = chars.containsAll(one.chars)
                    && chars.intersect(four.chars).size == 3
                    && chars.containsAll(seven.chars)
                    && chars.intersect(eight.chars).size == 6

                return if (match) zero else null
            }

            private fun isOne(chars: Set<Char>): Num? {
                return if (chars.size == one.size) one else null
            }

            private fun isTwo(chars: Set<Char>): Num? {
                if (chars.size != two.size) return null

                val match = chars.intersect(one.chars).size == 1
                    && chars.intersect(four.chars).size == 2
                    && chars.intersect(seven.chars).size == 2
                    && chars.intersect(eight.chars).size == 5

                return if (match) two else null
            }

            private fun isThree(chars: Set<Char>): Num? {
                if (chars.size != three.size) return null

                val match = chars.containsAll(one.chars)
                    && chars.intersect(four.chars).size == 3
                    && chars.containsAll(seven.chars)
                    && chars.intersect(eight.chars).size == 5

                return if (match) three else null
            }

            private fun isFour(chars: Set<Char>): Num? {
                return if (chars.size == four.size) one else null
            }

            private fun isFive(chars: Set<Char>): Num? {
                if (chars.size != five.size) return null

                val match = chars.intersect(one.chars).size == 1
                    && chars.intersect(four.chars).size == 3
                    && chars.intersect(seven.chars).size == 2
                    && chars.intersect(eight.chars).size == 5

                return if (match) five else null
            }

            private fun isSix(chars: Set<Char>): Num? {
                if (chars.size != six.size) return null

                val match = chars.intersect(one.chars).size == 1
                    && chars.intersect(four.chars).size == 3
                    && chars.intersect(seven.chars).size == 2
                    && chars.intersect(eight.chars).size == 6

                return if (match) six else null
            }

            private fun isSeven(chars: Set<Char>): Num? {
                return if (chars.size == seven.size) one else null
            }

            private fun isEight(chars: Set<Char>): Num? {
                return if (chars.size == eight.size) one else null
            }

            private fun isNine(chars: Set<Char>): Num? {
                if (chars.size != nine.size) return null

                val match = chars.containsAll(one.chars)
                    && chars.containsAll(four.chars)
                    && chars.containsAll(seven.chars)
                    && chars.intersect(eight.chars).size == 6

                return if (match) nine else null
            }
        }

        return input.fold(0) {acc, row ->
            acc + (Display(row).detectNumbers().decodeOutput())
        }
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

