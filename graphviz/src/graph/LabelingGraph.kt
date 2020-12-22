package graph

import api.DrawingApi
import java.util.HashSet
import java.util.function.Consumer

class LabelingGraph(drawingApi: DrawingApi, vertices: Collection<Vertex>?) : Graph(drawingApi) {
    private val vertices: Set<Vertex>
    public override fun doDraw() {
        vertices.forEach(Consumer { vertex: Vertex ->
            drawVertex(vertex.index)
            vertex.edges.forEach(Consumer { edge: Edge? -> drawEdge(edge!!) })
        })
    }

    override val numberOfVertices: Int = vertices!!.size

    init {
        this.vertices = HashSet(vertices)
    }
}