package graph

import api.DrawingApi
import api.DrawingUtils.CIRCLE_RADIUS
import api.Point
import java.util.HashMap
import java.util.HashSet

abstract class Graph(private val drawingApi: DrawingApi) {
    private val drawnVertices: MutableMap<Int, Point>
    private val drawnEdges: MutableSet<Edge>
    private var factory: PointsFactory? = null
    fun drawGraph() {
        factory = PointsFactory(
                drawingApi.drawingAreaWidth,
                drawingApi.drawingAreaHeight,
                numberOfVertices
        )
        doDraw()
        drawingApi.visualize()
    }

    protected fun drawVertex(index: Int): Point {
        if (drawnVertices.containsKey(index)) {
            return drawnVertices[index]!!
        }
        val p = factory!!.getPoint(index)
        drawnVertices[index] = p
        drawingApi.drawCircle(p, CIRCLE_RADIUS / 2)
        return p
    }

    protected fun drawEdge(edge: Edge) {
        if (drawnEdges.contains(edge)) return
        val p1 = drawVertex(edge.from)
        val p2 = drawVertex(edge.to)
        drawingApi.drawLine(p1, p2)
        drawnEdges.add(edge)
    }

    protected abstract fun doDraw()
    protected abstract val numberOfVertices: Int

    init {
        drawnVertices = HashMap()
        drawnEdges = HashSet()
    }
}