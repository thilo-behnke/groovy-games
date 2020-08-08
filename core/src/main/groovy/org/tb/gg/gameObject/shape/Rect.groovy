package org.tb.gg.gameObject.shape


import org.tb.gg.global.geom.Vector
import org.tb.gg.global.math.MathConstants
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Rect extends Shape {
    Vector topLeft
    Vector dim

    Rect(Vector topLeft, Vector dim) {
        this.topLeft = topLeft
        this.dim = dim
    }

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        renderDestination.drawRect(topLeft, dim, options)
    }

    @Override
    boolean isPointWithin(Vector pos) {
        def topLeft = topLeft
        def topRight = topRight
        return topLeft.x <= pos.x && pos.x <= topRight.x && bottomLeft.y <= pos.y && pos.y <= topRight.y
    }

    // TODO: Add cache.
    @Override
    Vector getCenter() {
        topLeft + dim * Vector.invertYVector() / 2.0
    }

    @Override
    void setCenter(Vector pos) {
        topLeft = pos - dim * Vector.invertYVector() / 2.0
    }

    Line getTopBorder() {
        return new Line(topLeft, topRight)
    }

    Line getRightBorder() {
        return new Line(topRight, bottomRight)
    }

    Line getBottomBorder() {
        return new Line(bottomLeft, bottomRight)
    }

    Line getLeftBorder() {
        return new Line(topLeft, bottomLeft)
    }

    Vector getTopRight() {
        return topLeft + new Vector(x: dim.x)
    }

    Vector getBottomLeft() {
        return topLeft + new Vector(y: -dim.y)
    }

    Vector getBottomRight() {
        return topLeft + new Vector(x: dim.x, y: - dim.y)
    }
}
