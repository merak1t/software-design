package api

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Shape
import javafx.stage.Stage
import java.util.*
import java.util.function.Consumer

class DrawingApiJavaFX : AbstractDrawingApi() {
    override fun drawCircle(p: Point, radius: Double) {
        shapes.add(Circle(p.x, p.y, radius))
    }

    override fun drawLine(p1: Point, p2: Point) {
        shapes.add(Line(p1.x, p1.y, p2.x, p2.y))
    }

    override fun visualize() {
        Application.launch(GraphApplication::class.java)
    }

    class GraphApplication : Application() {
        override fun start(stage: Stage) {
            stage.title = "JavaFx Graph"
            val root = Group()
            shapes.forEach(Consumer { shape: Shape? -> root.children.add(shape) })
            stage.scene = Scene(root, DrawingUtils.WIDTH.toDouble(), DrawingUtils.HEIGHT.toDouble())
            stage.show()
        }
    }

    companion object {
        private var shapes: MutableList<Shape> = ArrayList()
    }

}