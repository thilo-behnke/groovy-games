package org.tb.gg.gameObject.shape

import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class ShapeComposite implements Shape {
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
    Vector getClosestPointInDirectionFromCenter(Vector direction) {
        // TODO: Implement, complicated...
        return null
    }

    @Override
    boolean isPointWithin(Vector pos) {
        return shapes.find { it.isPointWithin(pos) }
    }

    @Override
    boolean doesOverlapWith(Shape shape) {
        return shapes.find { it.doesOverlapWith(shape) }
    }

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        shapes.each { it.render(renderDestination, options) }
    }
}
