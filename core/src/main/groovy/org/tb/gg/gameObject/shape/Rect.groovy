package org.tb.gg.gameObject.shape


import org.tb.gg.global.geom.Vector
import org.tb.gg.global.math.MathConstants
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions

class Rect extends Shape {
    Vector center
    Vector dim
    BigDecimal rotation

    // TODO: Use center instead of topLeft to construct.
    Rect(Vector topLeft, Vector dim, BigDecimal rotation = 0) {
        this.center = topLeft + new Vector(x: dim.x, y: -dim.y) / 2.0
        this.dim = dim.abs()
        this.rotation = rotation
    }

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        def topLeftWithoutRotation = center + new Vector(x: -dim.x, y: dim.y) / 2.0
        renderDestination.drawRect(topLeftWithoutRotation, dim, rotation.toFloat(), options)
    }

    @Override
    boolean isPointWithin(Vector pos) {
        return topLeft.x <= pos.x && pos.x <= topRight.x && bottomLeft.y <= pos.y && pos.y <= topRight.y
    }

    BigDecimal getSize() {
        return (dim.x * dim.y).abs()
    }

    Line getTopEdge() {
        return new Line(topLeft, topRight)
    }

    Line getRightEdge() {
        return new Line(topRight, bottomRight)
    }

    Line getBottomEdge() {
        return new Line(bottomLeft, bottomRight)
    }

    Line getLeftEdge() {
        return new Line(topLeft, bottomLeft)
    }

    // TopLeft with rotation = 0.
    Vector originalTopLeft() {
        center + new Vector(x: -dim.x / 2, y: dim.y / 2)
    }

    Vector getTopLeft() {
        def centerToCorner = new Vector(x: -dim.x, y: dim.y) / 2.0
        return center + centerToCorner.rotate(rotation)
    }

    Vector getTopRight() {
        def centerToCorner = new Vector(x: dim.x, y: dim.y) / 2.0
        return center + centerToCorner.rotate(rotation)
    }

    Vector getBottomLeft() {
        def centerToCorner = new Vector(x: -dim.x, y: -dim.y) / 2.0
        return center + centerToCorner.rotate(rotation)
    }

    Vector getBottomRight() {
        def centerToCorner = new Vector(x: dim.x, y: -dim.y) / 2.0
        return center + centerToCorner.rotate(rotation)
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
        return new Rect(topLeft.copy(), dim.copy(), rotation)
    }

    @Override
    void rotate(BigDecimal radians) {
        this.rotation = (rotation + radians).remainder(2.0 * MathConstants.PI)
    }
}
