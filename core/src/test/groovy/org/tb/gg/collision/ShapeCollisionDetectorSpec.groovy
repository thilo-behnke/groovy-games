package org.tb.gg.collision

import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.gameObject.shape.Point
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

class ShapeCollisionDetectorSpec extends Specification {

    ShapeCollisionDetector shapeCollisionDetector

    void setup() {
        shapeCollisionDetector = new ShapeCollisionDetector()
    }

    @Unroll
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

    @Unroll
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

    @Unroll
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

    @Unroll
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

    @Unroll
    def 'rect <-> rect'() {
        expect:
        shapeCollisionDetector.detect(rect1, rect2) == doCollide
        where:
        rect1                                                          | rect2                                                           | doCollide
        new Rect(Vector.unitVector(), Vector.unitVector() * 2.0)       | new Rect(Vector.zeroVector(), Vector.unitVector() * 5.0)        | true
        new Rect(Vector.unitVector() * 2.0, Vector.unitVector() * 2.0) | new Rect(Vector.unitVector() * 2.5, Vector.unitVector() * 5.0)  | true
        new Rect(Vector.unitVector(), Vector.unitVector() * 2.0)       | new Rect(Vector.unitVector() * 2.0, Vector.unitVector() * 1.0)  | true
        new Rect(Vector.unitVector(), Vector.unitVector() * 2.0)       | new Rect(Vector.unitVector() * 2.1, Vector.unitVector() * 1.0)  | false
        new Rect(Vector.unitVector(), Vector.unitVector() * 2.0)       | new Rect(Vector.unitVector() * 2.0, Vector.unitVector() * 0.9)  | false
        new Rect(Vector.unitVector() * 2.0, Vector.unitVector() * 5.0) | new Rect(Vector.unitVector() * -2.0, Vector.unitVector() * 1.0) | false
    }

    @Unroll
    def 'rect <-> line'() {
        expect:
        shapeCollisionDetector.detect(rect, line) == doCollide
        where:
        rect                                                     | line                                                            | doCollide
        new Rect(Vector.unitVector(), Vector.unitVector() * 2.0) | new Line(Vector.zeroVector(), Vector.unitVector())              | true
        new Rect(Vector.unitVector(), Vector.unitVector() * 2.0) | new Line(new Vector(x: 1, y: 1), new Vector(x: 2, y: 1))        | true
        new Rect(Vector.unitVector(), Vector.unitVector() * 2.0) | new Line(new Vector(x: 1, y: 3), new Vector(x: 2, y: 4))        | false
        new Rect(Vector.unitVector(), Vector.unitVector() * 2.0) | new Line(Vector.unitVector() * 2.0, Vector.unitVector() * 5.0)  | false
        new Rect(Vector.unitVector(), Vector.unitVector() * 2.0) | new Line(Vector.unitVector() * 5.0, new Vector(x: 5.0, y: 6.0)) | false
    }

    @Unroll
    def 'rect <-> point'() {
        expect:
        shapeCollisionDetector.detect(rect, point) == doCollide
        where:
        rect                                                            | point                                      | doCollide
        new Rect(Vector.unitVector(), Vector.unitVector() * 2.0)        | new Point(pos: Vector.unitVector())        | true
        new Rect(Vector.zeroVector(), Vector.unitVector() * 5.0)        | new Point(pos: new Vector(x: 1, y: -1))    | true
        new Rect(Vector.unitVector() + 2.0, Vector.unitVector() * 2.0)  | new Point(pos: new Vector(x: 3, y: 3))     | true
        new Rect(Vector.zeroVector(), Vector.unitVector() * 5.0)        | new Point(pos: Vector.unitVector())        | false
        new Rect(Vector.unitVector() * 3.0, Vector.unitVector() * 2.0)  | new Point(pos: Vector.unitVector() * -2.0) | false
        new Rect(Vector.unitVector() * -3.0, Vector.unitVector() * 2.0) | new Point(pos: Vector.unitVector() * 10.0) | false
    }

    @Unroll
    def 'line <-> line'() {
        expect:
        shapeCollisionDetector.detect(line1, line2) == doCollide
        where:
        line1                                                     | line2                                                        | doCollide
        new Line(Vector.zeroVector(), Vector.unitVector())        | new Line(Vector.zeroVector(), Vector.unitVector())           | true
        new Line(Vector.unitVector(), Vector.unitVector() * -1.0) | new Line(Vector.zeroVector(), Vector.unitVector())           | true
        new Line(Vector.zeroVector(), Vector.unitVector() * 2.0)  | new Line(new Vector(x: 0, y: 1), new Vector(x: 1, y: 0))     | true
        new Line(Vector.zeroVector(), Vector.unitVector() * 2.0)  | new Line(new Vector(x: 0, y: 1.5), new Vector(x: 1.5, y: 0)) | true
        new Line(Vector.zeroVector(), new Vector(x: -2, y: -3))   | new Line(new Vector(x: -1, y: -1), new Vector(x: -1, y: -4)) | true
        new Line(Vector.zeroVector(), Vector.unitVector() * -2.0) | new Line(new Vector(x: -1, y: -1), new Vector(x: 4, y: 4))   | true
        new Line(Vector.zeroVector(), new Vector(x: -2, y: -3))   | new Line(new Vector(x: -1, y: -1), new Vector(x: -3, y: -4)) | false
        new Line(Vector.zeroVector(), Vector.unitVector() * 2.0)  | new Line(new Vector(x: 3, y: 3), new Vector(x: 4, y: 4))     | false
        new Line(Vector.zeroVector(), Vector.unitVector() * -2.0) | new Line(new Vector(x: 1, y: 1), new Vector(x: 4, y: 4))     | false
        new Line(Vector.unitVector(), new Vector(x: 2, y: 1))     | new Line(new Vector(x: 3, y: 3), new Vector(x: 4, y: 3))     | false
    }

    @Unroll
    def 'line <-> point'() {
        expect:
        shapeCollisionDetector.detect(line, point) == doCollide
        where:
        line                                                      | point                                     | doCollide
        new Line(Vector.zeroVector(), Vector.unitVector())        | new Point(pos: Vector.zeroVector())       | true
        new Line(Vector.unitVector(), Vector.unitVector() * -1.0) | new Point(pos: Vector.unitVector())       | true
        new Line(Vector.zeroVector(), Vector.unitVector() * 2.0)  | new Point(pos: Vector.unitVector() * 2.0) | true
        new Line(Vector.unitVector(), Vector.unitVector() * -1.0) | new Point(pos: Vector.unitVector() * 0.8) | true
        new Line(Vector.unitVector(), Vector.unitVector() * -1.0) | new Point(pos: Vector.unitVector() * 2.0) | false
        new Line(Vector.unitVector(), Vector.unitVector() * -1.0) | new Point(pos: Vector.unitVector() * 2.0) | false
        new Line(Vector.unitVector(), Vector.unitVector() * 10.0) | new Point(pos: new Vector(x: 9, y: 10))   | false
        new Line(new Vector(x: 4, y: 8), new Vector(x: 9, y: 4))  | new Point(pos: new Vector(x: 5, y: 5))    | false
    }
}
