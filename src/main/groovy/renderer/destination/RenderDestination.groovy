package renderer.destination

import global.geom.Vector
import renderer.renderObjects.Renderable

import java.awt.Canvas

interface RenderDestination<T extends Vector> {
    void draw(Renderable renderable)
    void drawLine(T start, T end)
}

