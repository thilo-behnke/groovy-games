package global.geom

import global.math.MathConstants
import groovy.transform.ToString

@ToString
class CircleDesc {
    Vector center
    BigDecimal radius
}

class InvalidCircleOperationException extends Exception {
    InvalidCircleOperationException(String message) {
        super(message)
    }
}

class CircleOperations {
    static Vector getPointOnCircleInRadians(CircleDesc circleDesc, BigDecimal radians) {
        def cos = BigDecimal.valueOf(Math.cos(radians)).setScale(MathConstants.ctx.precision, MathConstants.ctx.roundingMode)
        def sin = BigDecimal.valueOf(Math.sin(radians)).setScale(MathConstants.ctx.precision, MathConstants.ctx.roundingMode)
        circleDesc.center + new Vector(x: cos, y: sin) * circleDesc.radius
    }

    static Vector getPointOnCircleFromOtherPointInRadians(CircleDesc circleDesc, BigDecimal radians, Vector point) {
        if (!isPointOnCircle(circleDesc, point)) {
            throw new InvalidCircleOperationException("Point ${point} is not on Circle ${CircleDesc}")
        }
        def pointNorm = (point - circleDesc.center).normalize()
        def beta = Math.acos(pointNorm.x)
        def alpha = radians + beta
        def cos = BigDecimal.valueOf(Math.cos(alpha)).setScale(MathConstants.ctx.precision, MathConstants.ctx.roundingMode)
        def sin = BigDecimal.valueOf(Math.sin(alpha)).setScale(MathConstants.ctx.precision, MathConstants.ctx.roundingMode)
        def newPoint = new Vector(x: cos, y: sin)
        circleDesc.center + newPoint
    }

    private static boolean isPointOnCircle(CircleDesc circleDesc, Vector point) {
        def pointProjected = (point - circleDesc.center)
        pointProjected.length() == circleDesc.radius
    }
}
