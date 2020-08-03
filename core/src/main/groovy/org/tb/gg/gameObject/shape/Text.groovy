package org.tb.gg.gameObject.shape

import org.tb.gg.collision.Collidable
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Text implements Shape {
    Vector pos
    String text

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        renderDestination.drawText(pos, text, options)
    }

    @Override
    boolean isPointWithin(Vector pos) {
        // TODO: Given this definition, is text really a shape?
        return false
    }

    @Override
    Vector getClosestPointInDirectionFromCenter(Vector direction) {
        return null
    }

    @Override
    boolean doesOverlapWith(Shape shape) {
        return false
    }

    @Override
    Vector getCenter() {
        return null
    }
}