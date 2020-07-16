package global.geom

import groovy.transform.EqualsAndHashCode


// TODO: Make immutable.

@EqualsAndHashCode(includes = ['x', 'y'])
class Vector {
    BigDecimal x
    BigDecimal y

    private final static unit = new Vector(x: 1, y: 1)
    private final static zero = new Vector(x: 0, y: 0)

    Vector plus(Vector b) {
        def newX = x.add(b.x)
        def newY = y.add(b.y)
        return new Vector(x: newX, y: newY)
    }

    Vector plus(Float n) {
        def newX = x + n
        def newY = y + n
        return new Vector(x: newX, y: newY)
    }

    Vector minus(Vector b) {
        def newX = x - b.x
        def newY = y - b.y
        return new Vector(x: newX, y: newY)
    }

    Vector multiply(Float n) {
        def newX = x * n
        def newY = y * n
        return new Vector(x: newX, y: newY)
    }

    Vector multiply(Vector b) {
        def newX = x * b.x
        def newY = y * b.y
        return new Vector(x: newX, y: newY)
    }

    Vector div(double n) {
        def newX = x / n
        def newY = y / n
        return new Vector(x: newX, y: newY)
    }

    Float length() {
        return Math.sqrt(x ** 2 + y ** 2)
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
