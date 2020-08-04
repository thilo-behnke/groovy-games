package org.tb.gg.renderer.shape

import org.tb.gg.gameObject.shape.Line
import org.tb.gg.global.geom.Vector
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class LineSpec extends Specification {

    Line line

    void setup() {
        line = new Line(new Vector(x: 0, y: 0), Vector.unitVector() * 4.0)
    }

    @Unroll
    def 'isPointWithin: should identify points to be outside of the line'() {
        expect:
        !line.isPointWithin(point)
        where:
        point                      | _
        new Vector(x: 2, y: 3)     | _
        new Vector(x: 4, y: 5)     | _
        new Vector(x: 1, y: 2)     | _
        new Vector(x: -1, y: -2)   | _
        new Vector(x: -4, y: -5)   | _
        new Vector(x: -2, y: -3)   | _
        new Vector(x: -2, y: -2.0) | _
        new Vector(x: 89, y: 83)   | _
    }

    @Unroll
    def 'isPointWithin: should identify points to be on the line'() {
        expect:
        line.isPointWithin(point)
        where:
        point                      | _
        new Vector(x: 0, y: 0)     | _
        new Vector(x: 2, y: 2.0)   | _
        new Vector(x: 2.0, y: 2.0) | _
        new Vector(x: 3.0, y: 3.0) | _
        new Vector(x: 4.0, y: 4.0) | _
    }

    @Unroll
    def 'setCenter'() {
        expect:
        line.setCenter(newCenter)
        line.start == start
        line.end == end
        where:
        newCenter                 || start                     | end
        new Vector(x: 0, y: 0)    || new Vector(x: -2, y: -2)  | new Vector(x: 2, y: 2)
        new Vector(x: 4, y: 4)    || new Vector(x: 2, y: 2)    | new Vector(x: 6, y: 6)
        new Vector(x: 883, y: 12) || new Vector(x: 881, y: 10) | new Vector(x: 885, y: 14)
    }
}
