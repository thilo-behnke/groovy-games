package org.tb.gg.renderer.shape

import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class RectangleSpec extends Specification {
    Rect rect

    void setup() {
        rect = new Rect(Vector.zeroVector(), new Vector(x: 2, y: 5))
    }

    @Unroll
    def 'should identify points to be outside of the rect'() {
        expect:
        !rect.isPointWithin(point)
        where:
        point                    | _
        new Vector(x: 2, y: 3)   | _
        new Vector(x: 4, y: 5)   | _
        new Vector(x: 1, y: 2)   | _
        new Vector(x: -1, y: -2) | _
        new Vector(x: -4, y: -5) | _
        new Vector(x: -2, y: -3) | _
        new Vector(x: 89, y: 83) | _
    }

    @Unroll
    def 'should identify points to be inside of the rect'() {
        expect:
        rect.isPointWithin(point)
        where:
        point                       | _
        new Vector(x: 0, y: -1)     | _
        new Vector(x: 0.5, y: -2.3) | _
        new Vector(x: 0.7, y: -2.2) | _
    }
}
