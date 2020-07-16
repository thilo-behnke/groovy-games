package renderer.destination

import global.geom.Vector

import java.awt.*
import java.awt.geom.Line2D

// Does not work...
class Canvas2dDestination implements RenderDestination<Vector> {
    private Canvas canvas
    private Graphics2D graphics

    Canvas2dDestination() {
        canvas = new Canvas()
        graphics = (Graphics2D) canvas.getGraphics()
    }

    @Override
    void drawLine(Vector start, Vector end) {
        graphics.draw(new Line2D.Float(start.x, start.y, end.x, end.y))
    }

    @Override
    void drawPoint(Vector pos) {

    }

    @Override
    void refresh() {

    }
}
