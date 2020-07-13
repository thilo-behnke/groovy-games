package renderer.destination

import global.geom.FVector
import renderer.renderObjects.Renderable

import java.awt.*
import java.awt.geom.Line2D

class Canvas2dDestination implements RenderDestination<FVector> {
    private Canvas canvas
    private Graphics2D graphics

    Canvas2dDestination() {
        canvas = new Canvas()
        graphics = (Graphics2D) canvas.getGraphics()
    }

    @Override
    void draw(Renderable renderable) {
        renderable.render(this)
    }

    @Override
    void drawLine(FVector start, FVector end) {
        graphics.draw(new Line2D.Float(start.x, start.y, end.x, end.y))
    }
}
