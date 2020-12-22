package api

import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.geom.Ellipse2D
import java.awt.geom.Line2D
import java.util.*
import java.util.function.Consumer
import kotlin.system.exitProcess

class DrawingApiAwt : AbstractDrawingApi() {
    private val shapes: MutableList<Shape> = ArrayList()
    override fun drawCircle(p: Point, radius: Double) {
        val x = radius * 2
        shapes.add(Ellipse2D.Double(p.x - radius, p.y - radius, x, x))
    }


    override fun drawLine(p1: Point, p2: Point) {
        shapes.add(Line2D.Double(p1.x, p1.y, p2.x, p2.y))
    }

    override fun visualize() {
        val frame: Frame = GraphFrame()
        frame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(event: WindowEvent) {
                exitProcess(0)
            }
        })
        frame.setSize(DrawingUtils.WIDTH, DrawingUtils.HEIGHT)
        frame.isVisible = true
    }

    private inner class GraphFrame : Frame("Awt Graph") {
        override fun paint(g: Graphics) {
            super.paint(g)
            val g2 = g as Graphics2D
            shapes.forEach(Consumer { shape: Shape? ->
                if (shape is Line2D.Double) {
                    g2.paint = Color.BLUE
                    g2.draw(shape)
                } else {
                    g2.paint = Color.BLACK
                    g2.fill(shape)
                }
            })
        }
    }
}