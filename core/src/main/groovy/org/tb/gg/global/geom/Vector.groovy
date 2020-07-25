package org.tb.gg.global.geom

import org.tb.gg.global.math.MathConstants
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

// TODO: Make immutable.

@EqualsAndHashCode(includes = ['x', 'y'])
@ToString
class Vector {
    BigDecimal x
    BigDecimal y

    private final static unit = new Vector(x: 1, y: 1)
    private final static zero = new Vector(x: 0, y: 0)
    private final static invertY = new Vector(x: 1, y: -1)

    // TODO: Typing does not work.
    BigDecimal getAt(int i) {
        switch (i) {
            case 0: return x
            case 1: return y
        }
        throw new IllegalArgumentException("No such element $i")
    }

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
        (xPow + yPow).sqrt(MathConstants.ctx)
    }

    Vector normalize() {
        return new Vector(x: x, y: y) / this.length()
    }

    static unitVector() {
        return unit
    }

    static zeroVector() {
        return zero
    }

    static invertYVector() {
        return invertY
    }
}
