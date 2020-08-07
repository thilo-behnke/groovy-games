package org.tb.gg.collision

import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.global.geom.Vector
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ShapeCollisionHandlerSpec extends Specification {

    def 'circle <-> circle'() {
        expect:
        ShapeCollisionDetector.detectCollision(circle1, circle2) == doOverlap
        where:
        circle1                                            | circle2                                                    | doOverlap
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector(), radius: 2)         | true
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 2.0, radius: 4)   | true
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 5.0, radius: 1)   | true
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 6.0, radius: 3)   | true
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 8.0, radius: 1)   | false
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 7.0, radius: 1)   | false
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 6.0, radius: 2)   | false
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 22.0, radius: 10) | false
    }

}
