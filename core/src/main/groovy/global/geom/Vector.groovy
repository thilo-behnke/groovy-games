package global.geom

import groovy.transform.EqualsAndHashCode

import java.math.MathContext
import java.math.RoundingMode


// TODO: Make immutable.

@EqualsAndHashCode(includes = ['x', 'y'])
class Vector {
    BigDecimal x
    BigDecimal y

    private final static unit = new Vector(x: 1, y: 1)
    private final static zero = new Vector(x: 0, y: 0)
    private final static ctx = new MathContext(5, RoundingMode.HALF_UP)

    Vector plus(Vector b) {
        def newX = x + b.x
        def newY = y + b.y
        return new Vector(x: newX, y: newY)
    }

    Vector plus(BigDecimal n) {
        def newX = x + n
        def newY = y + n
        return new Vector(x: newX, y: newY)
    }

    Vector minus(Vector b) {
        def newX = x - b.x
        def newY = y - b.y
        return new Vector(x: newX, y: newY)
    }

    Vector minus(BigDecimal n) {
        def newX = x - n
        def newY = y - n
        return new Vector(x: newX, y: newY)
    }

    Vector multiply(BigDecimal n) {
        def newX = x * n
        def newY = y * n
        return new Vector(x: newX, y: newY)
    }

    Vector multiply(Vector b) {
        def newX = x * b.x
        def newY = y * b.y
        return new Vector(x: newX, y: newY)
    }

    // TODO: Write tests.
    Vector div(BigDecimal n) {
        def newX = x / n
        def newY = y / n
        return new Vector(x: newX, y: newY)
    }

    BigDecimal length() {
        def xPow = x.pow(2)
        def yPow = y.pow(2)
        (xPow + yPow).sqrt(ctx)
    }

    Vector normalize() {
        return this / this.length()
    }

    static unitVector() {
        return unit
    }

    static zeroVector() {
        return zero
    }
}
