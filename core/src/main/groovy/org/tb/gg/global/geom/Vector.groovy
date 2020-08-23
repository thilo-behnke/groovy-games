package org.tb.gg.global.geom

import ch.obermuhlner.math.big.BigDecimalMath
import groovy.transform.ToString
import org.tb.gg.global.math.MathConstants

@ToString
class Vector {
    final BigDecimal x
    final BigDecimal y

    private final static unit = new Vector(x: 1, y: 1)
    private final static zero = new Vector(x: 0, y: 0)
    private final static invertY = new Vector(x: 1, y: -1)

    Vector(Map map) {
        // Default values if no x or y are given.
        def xVal = map.x ?: 0.0
        def yVal = map.y ?: 0.0
        // TODO: Handle different number types
        x = xVal instanceof BigDecimal ? (BigDecimal) xVal : BigDecimal.valueOf(xVal)
        y = yVal instanceof BigDecimal ? (BigDecimal) yVal : BigDecimal.valueOf(yVal)
    }

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
        def newX = x != 0 ? x / n : 0
        def newY = y != 0 ? y / n : 0
        return new Vector(x: newX, y: newY)
    }

    Vector rotate(BigDecimal radians) {
        def newX = (x * BigDecimalMath.cos(radians, MathConstants.ctx) - y * BigDecimalMath.sin(radians, MathConstants.ctx)).round(MathConstants.ctx)
        def newY = (x * BigDecimalMath.sin(radians, MathConstants.ctx) + y * BigDecimalMath.cos(radians, MathConstants.ctx)).round(MathConstants.ctx)
        return new Vector(x: newX, y: newY)
    }

    Vector abs() {
        return new Vector(x: x.abs(), y: y.abs())
    }

    BigDecimal sum() {
        x + y
    }

    // TODO: Write tests.
    BigDecimal cross(Vector b) {
        x * b.y - y * b.x
    }

    BigDecimal dot(Vector b) {
        x * b.x + y * b.y
    }

    boolean isInSameDirection(Vector b) {
        dot(b) > 0
    }

    Vector clampOnRange(Vector min, Vector max) {
        return new Vector(x: clamp(x, min.x, max.x), y: clamp(y, min.y, max.y))
    }

    Vector scale(BigDecimal scaleFactor) {
        multiply(scaleFactor)
    }

    private static clamp(BigDecimal comp, BigDecimal min, BigDecimal max) {
        if (comp < min) {
            return min
        } else if (comp > max) {
            return max
        }
        return comp
    }

    BigDecimal length() {
        def xPow = x.pow(2)
        def yPow = y.pow(2)
        BigDecimalMath.sqrt(xPow + yPow, MathConstants.ctx)
    }

    Vector normalize() {
        if (this == zeroVector()) {
            return new Vector(x: x, y: y)
        }
        return new Vector(x: x, y: y) / this.length()
    }

    Vector signum() {
        return new Vector(x: x.signum(), y: y.signum())
    }

    // TODO: This gives one out of two possible perpendiculars. Is there a way to parameterize the method?
    Vector perpendicular() {
        def norm = normalize()
        new Vector(x: norm.y, y: -norm.x)
    }

    Vector projectOnto(Vector a) {
        def aDot = a.dot(a)
        if (!aDot) {
            return a
        }
        return a * (a.dot(this) / aDot)
    }

    BigDecimal angleBetween(Vector b) {
        def angle = BigDecimalMath.atan2(b.y, b.x, MathConstants.ctx) - BigDecimalMath.atan2(y, x, MathConstants.ctx)
        return angle >= 0 ? angle : 2 * MathConstants.pi() + angle
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

    /**
     * Custom equals method for vector operations.
     * Instead of an absolute comparision of the x and y components, are small error margin is considered.
     * The reason for this implementation are operations sqrt that will lead to rounding issues.
     *
     * E.g. consider the case of comparing the length a normalized vector to the length of another vector.
     *
     * @param obj
     * @return true if the x and y components of both vectors only differ in a small defined margin.
     */
    @Override
    boolean equals(Object obj) {
        if (!obj instanceof Vector) {
            return false
        }
        Vector otherV = (Vector) obj
        def xDiff = (otherV.x - x).abs()
        def yDiff = (otherV.y - y).abs()
        return xDiff < 1e-4 && yDiff < 1e-4
    }

    // TODO: See the above equals implementation - is this hashcode then correct? Given that a small error margin is introduced for comparison.
    @Override
    int hashCode() {
        int result
        result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}

