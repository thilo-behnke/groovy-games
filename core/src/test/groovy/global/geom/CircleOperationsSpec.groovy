package global.geom

import global.math.MathConstants
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CircleOperationsSpec extends Specification {
    @Unroll
    def 'get point in circle on given radians: #desc'() {
        expect:
        CircleOperations.getPointOnCircleInRadians(circleDesc, radians) == point
        where:
        circleDesc                                               | radians                  || point                   || desc
        new CircleDesc(center: Vector.zeroVector(), radius: 1.0) | 0.0                      || new Vector(x: 1, y: 0)  || 'unit circle, start first quadrant'
        new CircleDesc(center: Vector.zeroVector(), radius: 1.0) | MathConstants.PI / 2     || new Vector(x: 0, y: 1)  || 'unit circle, end first quadrant'
        new CircleDesc(center: Vector.zeroVector(), radius: 1.0) | MathConstants.PI         || new Vector(x: -1, y: 0) || 'unit circle, end second quadrant'
        new CircleDesc(center: Vector.zeroVector(), radius: 1.0) | MathConstants.PI * 3 / 2 || new Vector(x: 0, y: -1) || 'unit circle, end third quadrant'
        new CircleDesc(center: Vector.zeroVector(), radius: 1.0) | MathConstants.PI * 2     || new Vector(x: 1, y: 0)  || 'unit circle, end fourth quadrant (full circle)'
        new CircleDesc(center: Vector.unitVector(), radius: 5.0) | MathConstants.PI / 2     || new Vector(x: 1, y: 6)  || 'unit circle with radius 5 at (1/1), add half pie'

    }

    @Unroll
    def 'get point in circle relative to other point on given radians: #desc'() {
        expect:
        CircleOperations.getPointOnCircleFromOtherPointInRadians(circleDesc, radians, otherPoint) == point
        where:
        circleDesc                                               | radians              | otherPoint             || point                              || desc
        new CircleDesc(center: Vector.zeroVector(), radius: 1.0) | 0.0                  | new Vector(x: 1, y: 0) || new Vector(x: 1, y: 0)             || 'unit circle, zero point'
        new CircleDesc(center: Vector.zeroVector(), radius: 1.0) | MathConstants.PI / 2 | new Vector(x: 1, y: 0) || new Vector(x: 0, y: 1)             || 'unit circle, add half pie'
        new CircleDesc(center: Vector.unitVector(), radius: 1.0) | MathConstants.PI / 4 | new Vector(x: 1, y: 2) || new Vector(x: 0.29289, y: 1.70711) || 'unit circle at (1/1), add quarter pie to end of first quadrant'
        new CircleDesc(center: Vector.unitVector(), radius: 8.0) | MathConstants.PI / 2 | new Vector(x: 1, y: 9) || new Vector(x: -7.0, y: 1.0)        || 'circle with radius 8 at (1/1), add half pie'
    }

    def 'should throw exception for point not on circle'() {
        given:
        def circleDesc = new CircleDesc(center: Vector.unitVector(), radius: 5)
        def pointNotOnCircle = new Vector(x: 5, y: 10)
        when:
        CircleOperations.getPointOnCircleFromOtherPointInRadians(circleDesc, 10.0, pointNotOnCircle)
        then:
        thrown Exception
    }

}
