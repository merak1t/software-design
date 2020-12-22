package graph

class Edge(val from: Int, val to: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Edge) return false
        return (from == other.from
                && to == other.to)
    }

    override fun hashCode(): Int {
        var result = from
        result = 31 * result + to
        return result
    }
}