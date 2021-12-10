
fun main() {
    data class Point(val x: Int, val y: Int): Comparable<Point> {
        override fun compareTo(other: Point): Int = when {
            y != other.y -> y - other.y
            else -> x - other.x
        }
    }

    fun Point.followingPoint(end: Point): Point {
        return when {
            x == end.x -> this.copy(y = y + 1)
            y == end.y -> this.copy(x = x + 1)
            x > end.x -> this.copy(x = x - 1, y = y + 1)
            else -> this.copy(x = x + 1, y = y + 1)
        }
    }

    open class PointRange(first: Point, second: Point) : Iterable<Point> {
        val start = if (first <= second) first else second
        val end = if (first <= second) second else first
        override fun iterator(): Iterator<Point> {
            return object : Iterator<Point> {
                var current: Point = start

                override fun next(): Point {
                    if (!hasNext()) throw NoSuchElementException()
                    val result = current
                    current = current.followingPoint(end)
                    return result
                }

                override fun hasNext(): Boolean = current <= end
            }
        }

        override fun toString(): String {
            return "[$start -> $end]"
        }
    }

    operator fun Point.rangeTo(other: Point) = PointRange(this, other)

    fun PointRange.hasNext() = start.x == end.x || start.y == end.y

    fun String.toPoint() = split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) }

    fun String.toPointRange() = split(" -> ").map { s -> s.toPoint() }.let { (start, end) -> start..end }

    fun List<PointRange>.calculateOverlaps(): Int {
        val diagram: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()
        forEach { pointRange ->
            pointRange.iterator().forEach { point ->
                diagram[point.x]?.let { x ->
                    x[point.y]?.let { y -> x[point.y] = x[point.y]!! + 1 } ?: run { x[point.y] = 1 }
                } ?: run { diagram[point.x] = mutableMapOf(point.y to 1) }
            }
        }
        return diagram.keys.sumOf { x -> diagram[x]?.count { (_, value) -> value >= 2 } ?: 0 }
    }

    fun part1(input: List<String>): Int {
        return input.map { row -> row.toPointRange() }.filter { it.hasNext() }
            .calculateOverlaps()
    }

    fun part2(input: List<String>): Int {
        return input.map { row -> row.toPointRange() }.calculateOverlaps()
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

