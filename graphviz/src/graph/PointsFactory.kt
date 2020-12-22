package graph

import api.DrawingUtils.MARGIN
import api.Point
import kotlin.math.cos
import kotlin.math.sin

class PointsFactory(width: Int, height: Int, pointsCount: Int) {
    private val dAlpha: Double
    private val center: Point
    private val radius: Double
    fun getPoint(index: Int): Point {
        val x = radius * cos(dAlpha * index) + center.x
        val y = radius * sin(dAlpha * index) + center.y
        return Point(x, y)
    }

    init {
        require(pointsCount != 0) { "Empty graph" }
        radius = width.coerceAtMost(height) / 2.0 - MARGIN
        center = Point(
                width / 2.0,
                height / 2.0
        )
        dAlpha = 2 * Math.PI / pointsCount
    }
}