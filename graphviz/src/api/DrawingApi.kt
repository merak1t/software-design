package api

interface DrawingApi {
    val drawingAreaWidth: Int
    val drawingAreaHeight: Int

    fun drawCircle(p: Point, radius: Double)
    fun drawLine(p1: Point, p2: Point)
    fun visualize()
}