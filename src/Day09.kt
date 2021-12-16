
fun main() {
    data class Point(val i: Int, val j: Int, val value: Int)
    fun getIndexes(i: Int, max: Int) = listOf(i - 1, i + 1).filter { it in 0 until max }
    fun getNeighbours(start: Point, heightmap: List<String>): Set<Point> {
        val maxI = heightmap.size
        val maxJ = heightmap[0].length
        val horizontalNeighbours = getIndexes(start.j, maxJ).map { Point(i = start.i, j = it, value = heightmap[start.i][it].toString().toInt()) }.toSet()
        val verticalNeighbours = getIndexes(start.i, maxI).map { Point(i = it, j = start.j, value = heightmap[it][start.j].toString().toInt()) }.toSet()

        return horizontalNeighbours + verticalNeighbours
    }

    fun List<String>.getLowestPoints() = this.foldIndexed(mutableListOf<Point>()) {i, acc, row ->
        row.mapIndexed { j, c ->
            val start = Point(i, j, this[i][j].toString().toInt())
            val isLowest = getNeighbours(start, this).map { it.value }.minOf {it} > c.toString().toInt()
            if (isLowest) start else null
        }
            .filterNotNull()
            .apply { acc.addAll(this) }
        acc
    }

    fun part1(input: List<String>): Int {
        return input.getLowestPoints()
            .fold(0) { acc, point -> acc + point.value + 1 }
    }

    fun getBasin(points: List<Point>, basin: List<Point>, heightmap: List<String>): List<Point> {
        val newPoints = points.flatMap { start -> getNeighbours(start, heightmap).filter { it.value in (start.value + 1) until 9 } }
        val result = basin + points
        if (newPoints.isEmpty()) return result
        return getBasin(newPoints, result, heightmap)
    }

    fun part2(input: List<String>): Int {
        return input.getLowestPoints()
            .map { point -> getBasin(listOf(point), listOf(), input).toSet() }
            .sortedByDescending { it.size }
            .take(3)
            .also { list -> list.map { l -> l.map { p -> p.value }.also { println("size: ${it.size}, - $it") } } }
            .fold(1) { acc, points -> acc * points.size }
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

