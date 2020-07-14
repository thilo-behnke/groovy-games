package renderer.destination

import global.geom.FVector

import java.awt.*
import java.awt.geom.Line2D

// Does not work...
class Canvas2dDestination implements RenderDestination<FVector> {
    private Canvas canvas
    private Graphics2D graphics

    Canvas2dDestination() {
        canvas = new Canvas()
        graphics = (Graphics2D) canvas.getGraphics()
    }

    @Override
    void drawLine(FVector start, FVector end) {
        graphics.draw(new Line2D.Float(start.x, start.y, end.x, end.y))
    }

    @Override
    void refresh() {

    }
}
