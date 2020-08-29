package org.tb.gg.gameObject.shape


import org.tb.gg.global.geom.Vector
import org.tb.gg.global.math.MathConstants
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Rect extends Shape {
    Vector topLeft
    Vector dim
    BigDecimal rotation

    Rect(Vector topLeft, Vector dim, BigDecimal rotation = 0) {
        this.topLeft = topLeft
        this.dim = dim.abs()
        this.rotation = rotation
    }

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        renderDestination.drawRect(topLeft, dim, rotation.toFloat(), options)
    }

    @Override
    boolean isPointWithin(Vector pos) {
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

    BigDecimal getSize() {
        return (dim.x * dim.y).abs()
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
        return topLeft + new Vector(x: dim.x).rotate(rotation)
    }

    Vector getBottomLeft() {
        return topLeft + new Vector(y: -dim.y).rotate(rotation)
    }

    Vector getBottomRight() {
        return topLeft + new Vector(x: dim.x, y: -dim.y).rotate(rotation)
    }

    BigDecimal diagonalLength() {
        topLeft.distance(center)
    }

    @Override
    Rect getBoundingRect() {
        return this
    }

    @Override
    Shape copy() {
        return new Rect(topLeft.copy(), dim.copy())
    }

    @Override
    void rotate(BigDecimal radians) {
        this.rotation = (rotation + radians).remainder(2.0 * MathConstants.PI)
    }
}
