package org.tb.gg.renderer.shape

import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.global.geom.Vector
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CircleSpec extends Specification {

    Circle circle

    void setup() {
        circle = new Circle(center: Vector.unitVector(), radius: 5)
    }

    @Unroll
    def 'identify points outside of circle'() {
        expect:
        !circle.isPointWithin(point)
        where:
        point                       | _
        Vector.unitVector() * 6.0   | _
        Vector.unitVector() * 12.0  | _
        Vector.unitVector() * 202.0 | _
        Vector.unitVector() * 5.0   | _
        new Vector(x: 89, y: 83)    | _
    }

    @Unroll
    def 'identify points inside of circle'() {
        expect:
        circle.isPointWithin(point)
        where:
        point                       | _
        Vector.unitVector() * 2.0   | _
        Vector.unitVector() * 4.0   | _
        new Vector(x: 1.2, y: 3.33) | _
    }

    @Unroll
    def 'overlapping with other circle'() {
        expect:
        circle.doesOverlapWith(otherCircle) == doOverlap
        where:
        otherCircle                                                | doOverlap
        new Circle(center: Vector.unitVector(), radius: 2)         | true
        new Circle(center: Vector.unitVector() * 2.0, radius: 4)   | true
        new Circle(center: Vector.unitVector() * 5.0, radius: 1)   | true
        new Circle(center: Vector.unitVector() * 6.0, radius: 3)   | true
        new Circle(center: Vector.unitVector() * 8.0, radius: 1)   | false
        new Circle(center: Vector.unitVector() * 7.0, radius: 1)   | false
        new Circle(center: Vector.unitVector() * 6.0, radius: 2)   | false
        new Circle(center: Vector.unitVector() * 22.0, radius: 10) | false
    }

}
