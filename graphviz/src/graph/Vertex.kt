package graph

import java.util.*
import java.util.stream.Collectors

class Vertex(val index: Int) {
    private val neighbours: MutableList<Int>
    fun addNeighbour(other: Int) {
        neighbours.add(other)
    }

    val edges: List<Edge>
        get() = neighbours.stream().map { x: Int? -> Edge(index, x!!) }.collect(Collectors.toList())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Vertex) return false
        return index == other.index
    }

    override fun hashCode(): Int {
        var result = index
        result = 31 * result + neighbours.hashCode()
        result = 31 * result + edges.hashCode()
        return result
    }

    init {
        neighbours = ArrayList()
    }
}