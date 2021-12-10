import java.time.Instant

fun main() {
    data class Board(
        val index: Int,
        val numbers: List<List<String>>,
        val markedRows: MutableMap<Int, MutableList<String>> = mutableMapOf(),
        val markedCols: MutableMap<Int, MutableList<String>> = mutableMapOf(),
        var finalNumber: Int? = null,
        var wonAt: Instant? = null,
        var wonRow: Int? = null,
        var wonCol: Int? = null,
        var isWon: Boolean = false,
    ) {
        fun markNumber(num: String) {
            numbers.forEachIndexed { col, column ->
                column.forEachIndexed { row, element ->
                    if (num == element) {
                        markNumberForColumn(num, col)
                        setWinnerForColumn(num.toInt(), col)
                        markNumberForRow(num, row)
                        setWinnerForRow(num.toInt(), row)
                    }
                }
            }
        }

        fun calculateResult(): Int {
            val unMarkedSum = numbers.foldIndexed(0) { col, acc, column ->
                acc + column.foldIndexed(0) { _, colAcc, element ->
                    if (element in markedCols[col]!!) colAcc
                    else colAcc + element.toInt()
                }
            }

            return finalNumber!! * unMarkedSum
        }

        private fun markNumberForColumn(num: String, col: Int) {
            if (markedCols[col] == null) markedCols[col] = mutableListOf(num)
            else markedCols[col]?.add(num)
        }

        private fun markNumberForRow(num: String, row: Int) {
            if (markedRows[row] == null) markedRows[row] = mutableListOf(num)
            else markedRows[row]?.add(num)
        }

        private fun setWinnerForColumn(num: Int, col: Int) {
            if (markedCols[col]?.size != 5) return
            wonCol = col
            finalNumber = num
            wonAt = Instant.now()
            isWon = true
        }

        private fun setWinnerForRow(num: Int, row: Int) {
            if (markedRows[row]?.size != 5) return
            wonRow = row
            finalNumber = num
            wonAt = Instant.now()
            isWon = true
        }
    }

    fun List<String>.prepareGame(): Pair<List<String>, List<Board>> {
        val order = this[0].split(",")
        val boards = this.drop(2)
            .windowed(size = 5, step = 6)
            .map { it.map { s -> s.split("\\s+".toRegex()).filter { c -> c !== "" } } }
            .mapIndexed { index, lists ->  Board(numbers = lists, index = index) }

        return order to boards
    }

    fun part1(input: List<String>): Int {
        val (order, boards) = input.prepareGame()
        order.forEach { number ->
            val winner = boards.find { it.isWon }
            if (winner != null) return@forEach
            boards.filter { !it.isWon }.forEach { board -> board.markNumber(number) }
        }
        return boards.find { it.isWon }!!.calculateResult()
    }

    fun part2(input: List<String>): Int {
        val (order, boards) = input.prepareGame()
        order.forEach { number ->
            val winners = boards.count { it.isWon }
            if (winners == boards.size) return@forEach
            boards.filter { !it.isWon }.forEach { board -> board.markNumber(number) }
        }
        return boards.sortedBy { it.wonAt }.last().calculateResult()
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

