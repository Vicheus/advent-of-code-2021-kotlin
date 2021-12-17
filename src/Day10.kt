import java.util.Stack

fun main() {
    val opposite = mapOf(
        ')' to '(',
        ']' to '[',
        '}' to '{',
        '>' to '<',
    )
    val opened = setOf('(', '[', '{', '<')
    val closed = setOf(')', ']', '}', '>')
    fun Char.processElement(store: Stack<Char>, opposite: Map<Char, Char>): Char? {
        val e = this
        if (e in opened) store.push(e)
        if (e in closed) {
            val last = store.peek()
            if (last == opposite[e]) store.pop()
            else return null
        }
        return e
    }
    fun part1(input: List<String>): Int {
        val weights = mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137,
        )
        return input.fold(mutableListOf<Int>()) { acc, row ->
            val store = Stack<Char>()
            val incorrect = row.firstOrNull { it.processElement(store, opposite) == null }
            incorrect?.let { acc.add(weights[it]!!) }
            acc
        }
            .sum()
    }

    fun part2(input: List<String>): Long {
        val weights = mapOf(
            '(' to 1,
            '[' to 2,
            '{' to 3,
            '<' to 4,
        )
        return input.fold(mutableListOf<Long>()) { acc, row ->
            val store = Stack<Char>()
            row.firstOrNull { it.processElement(store, opposite) == null } ?: acc.add(store.foldRight(0) { c, tot -> tot * 5 + weights[c]!!})
            acc
        }
            .sorted()
            .let { it[(it.size) / 2] }
    }

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

