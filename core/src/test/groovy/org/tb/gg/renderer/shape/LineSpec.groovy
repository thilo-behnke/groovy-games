package org.tb.gg.renderer.shape

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
    def 'should identify points to be outside of the line'() {
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
    def 'should identify points to be on the line'() {
        expect:
        line.isPointWithin(point)
        where:
        point                      | _
        new Vector(x: 0, y: 0)     | _
        // TODO: Broken, because normalize does not round... Maybe just round x and y when a vector is created?
        new Vector(x: 2, y: 2.0)   | _
        new Vector(x: 2.0, y: 2.0) | _
        new Vector(x: 3.0, y: 3.0) | _
        new Vector(x: 4.0, y: 4.0) | _
    }
}
