package org.tb.gg.global.geom

import ch.obermuhlner.math.big.BigDecimalMath
import org.tb.gg.global.math.MathConstants
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class VectorOperationSpec extends Specification {
    @Unroll
    def 'vector length: #desc'() {
        expect:
        v.length() == res
        where:
        v                                  || res    || desc
        Vector.unitVector()                || 1.4142 || "unit vector"
        new Vector(x: 3, y: 4)             || 5      || "no fracture length"
        new Vector(x: 593.11, y: 1903.002) || 1993.3 || "fracture length"
    }

    @Unroll
    def 'vector normalize: #desc'() {
        expect:
        v.normalize() == vRes
        where:
        v                                  || vRes                                         || desc
        Vector.unitVector()                || new Vector(x: 0.7071135624, y: 0.7071135624) || "unit vector"
        new Vector(x: 3, y: 4)             || new Vector(x: 0.6, y: 0.8)                   || "no fracture length"
        new Vector(x: 593.11, y: 1903.002) || new Vector(x: 0.2975517985, y: 0.9546992425) || "fracture length"
    }

    @Unroll
    def 'angleBetween'() {
        expect:
        // This leads to small rounding issues - maybe a different implementation than BigDecimal is needed?
        def angle = a.angleBetween(b)
        def diff = angle - vRes
        diff.abs() < 1e-4
        where:
        a                      | b                        || vRes
        Vector.unitVector()    | Vector.unitVector()      || 0
        new Vector(x: 1, y: 0) | Vector.unitVector()      || MathConstants.pi(4.0)
        new Vector(x: 1, y: 0) | new Vector(x: 0, y: 1)   || MathConstants.pi(2.0)
        new Vector(x: 1, y: 0) | new Vector(x: -1, y: 1)  || (MathConstants.PI * 3 / 4).round(MathConstants.ctx)
        new Vector(x: 1, y: 0) | new Vector(x: -1, y: 0)  || MathConstants.pi()
        new Vector(x: 1, y: 0) | new Vector(x: -1, y: -1) || (MathConstants.PI * 5 / 4).round(MathConstants.ctx)
        new Vector(x: 1, y: 0) | new Vector(x: 0, y: -1)  || (MathConstants.PI * 3 / 2).round(MathConstants.ctx)
        new Vector(x: 1, y: 0) | new Vector(x: 1, y: -1)  || (MathConstants.PI * 7 / 4).round(MathConstants.ctx)
    }

    @Unroll
    def 'rotate'() {
        expect:
        a.rotate(radians) == rotated
        where:
        a                        | radians               || rotated
        Vector.unitVector()      | 0.0                   || Vector.unitVector()
        new Vector(x: 1, y: 0)   | MathConstants.HALF_PI || new Vector(x: 0, y: 1)
        new Vector(x: 0, y: 1)   | MathConstants.HALF_PI || new Vector(x: -1, y: 0)
        new Vector(x: 0, y: 1)   | MathConstants.PI      || new Vector(x: 0, y: -1)
        new Vector(x: 1, y: 1)   | MathConstants.PI      || new Vector(x: -1, y: -1)
        new Vector(x: -1, y: -1) | MathConstants.PI      || new Vector(x: 1, y: 1)
    }
}
