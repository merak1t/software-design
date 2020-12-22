package api

import api.DrawingUtils.HEIGHT
import api.DrawingUtils.WIDTH

abstract class AbstractDrawingApi : DrawingApi {
    override val drawingAreaWidth: Int = WIDTH
    override val drawingAreaHeight: Int = HEIGHT
}