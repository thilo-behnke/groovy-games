package org.tb.gg.gameObject.shape

import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class ShapeComposite extends Shape {
    private List<Shape> shapes

    ShapeComposite(List<Shape> shapes) {
        this.shapes = shapes
    }

    @Override
    Vector getCenter() {
        // TODO: Implement, complicated...
        return null
    }

    @Override
    void setCenter(Vector pos) {
        // TODO: Implement, complicated...
    }

    @Override
    boolean isPointWithin(Vector pos) {
        return shapes.find { it.isPointWithin(pos) }
    }

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        shapes.each { it.render(renderDestination, options) }
    }

    @Override
    Rect getBoundingRect() {
        // TODO: Implement.
        return null
    }

    @Override
    Shape copy() {
        // TODO: Implement.
        return null
    }
}
