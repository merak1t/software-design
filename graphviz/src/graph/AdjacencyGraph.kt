package graph

import api.DrawingApi
import java.util.*
import java.util.function.Consumer
import java.util.stream.Stream


class AdjacencyGraph(drawingApi: DrawingApi, edges: Collection<Edge>?) : Graph(drawingApi) {
    private val edges: List<Edge>
    public override fun doDraw() {
        for (cur in 1..numberOfVertices) {
            drawVertex(cur)
        }
        edges.forEach(Consumer { edge: Edge? -> edge?.let { drawEdge(it) } })
    }

    override val numberOfVertices: Int = edges!!.stream()
            .flatMap { e: Edge -> Stream.of(e.from, e.to) }
            .max(Comparator.naturalOrder()).orElse(0)

    init {
        this.edges = ArrayList(edges!!)
    }
}