package org.tb.gg.collision

import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.gameObject.shape.Point
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import spock.lang.Specification

class ShapeCollisionDetectorSpec extends Specification {

    ShapeCollisionDetector shapeCollisionDetector

    void setup() {
        shapeCollisionDetector = new ShapeCollisionDetector()
    }

    def 'circle <-> circle'() {
        expect:
        shapeCollisionDetector.detect(circle1, circle2) == doCollide
        where:
        circle1                                            | circle2                                                    | doCollide
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector(), radius: 2)         | true
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 2.0, radius: 4)   | true
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 5.0, radius: 1)   | true
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 6.0, radius: 3)   | true
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 8.0, radius: 1)   | false
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 7.0, radius: 1)   | false
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 6.0, radius: 2)   | false
        new Circle(center: Vector.unitVector(), radius: 5) | new Circle(center: Vector.unitVector() * 22.0, radius: 10) | false
    }

    def 'circle <-> point'() {
        expect:
        shapeCollisionDetector.detect(circle, point) == doCollide
        where:
        circle                                                   | point                                      | doCollide
        new Circle(center: Vector.unitVector(), radius: 5)       | new Point(pos: Vector.unitVector())        | true
        new Circle(center: Vector.unitVector(), radius: 2)       | new Point(pos: Vector.unitVector() * 2.0)  | true
        new Circle(center: Vector.unitVector() * 4.0, radius: 1) | new Point(pos: Vector.unitVector() * 4.2)  | true
        new Circle(center: Vector.unitVector() * 4.0, radius: 1) | new Point(pos: Vector.unitVector() * 4.8)  | false
        new Circle(center: Vector.unitVector(), radius: 1)       | new Point(pos: Vector.unitVector() * 2.0)  | false
        new Circle(center: Vector.unitVector(), radius: 10)      | new Point(pos: Vector.unitVector() * 20.0) | false
    }

    def 'circle <-> line'() {
        expect:
        shapeCollisionDetector.detect(circle, line) == doCollide
        where:
        circle                                             | line                                                           | doCollide
        new Circle(center: Vector.unitVector(), radius: 5) | new Line(Vector.unitVector(), Vector.zeroVector())             | true
        new Circle(center: Vector.unitVector(), radius: 4) | new Line(Vector.unitVector() * 3.3, Vector.unitVector() * 4.4) | true
        new Circle(center: Vector.zeroVector(), radius: 1) | new Line(Vector.unitVector() * 0.7, Vector.unitVector() * 2.4) | true
        new Circle(center: Vector.unitVector(), radius: 2) | new Line(Vector.unitVector() * 3.3, Vector.unitVector() * 4.4) | false
        new Circle(center: Vector.zeroVector(), radius: 1) | new Line(Vector.unitVector() * 1.1, Vector.unitVector() * 4.4) | false
    }

    def 'circle <-> rect'() {
        expect:
        shapeCollisionDetector.detect(circle, rect) == doCollide
        where:
        circle                                                    | rect                                                            | doCollide
        new Circle(center: Vector.unitVector(), radius: 5)        | new Rect(Vector.unitVector(), Vector.unitVector() * 1.0)        | true
        new Circle(center: Vector.unitVector(), radius: 2)        | new Rect(Vector.zeroVector(), Vector.unitVector() * 2.0)        | true
        new Circle(center: Vector.unitVector(), radius: 3)        | new Rect(Vector.unitVector() * 2.0, Vector.unitVector() * 1.0)  | true
        new Circle(center: Vector.unitVector() * -2.0, radius: 3) | new Rect(Vector.unitVector() * -3.0, Vector.unitVector() * 1.0) | true
        new Circle(center: Vector.unitVector(), radius: 3)        | new Rect(Vector.unitVector() * 5.0, Vector.unitVector() * 1.0)  | false
        new Circle(center: Vector.unitVector(), radius: 10)       | new Rect(Vector.unitVector() * 11.0, Vector.unitVector() * 1.0) | false
        new Circle(center: Vector.unitVector(), radius: 3)        | new Rect(Vector.unitVector() * -3.0, Vector.unitVector() * 1.0) | false
    }

}
