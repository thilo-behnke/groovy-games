package gameObject.player

import gameObject.GameObject
import global.geom.CircleDesc
import global.geom.CircleOperations
import global.geom.Vector
import global.math.MathConstants

class Clock extends GameObject {
    final SECOND_CIRCLE_STEP = MathConstants.PI * 2 / 60
    final MINUTE_CIRCLE_STEP = MathConstants.PI * 2 / 60
    final HOUR_CIRCLE_STEP = MathConstants.PI * 2 / 24

    final CircleDesc circleDesc
    private Vector clockStart

    public Vector secondHandlePos
    public Vector minuteHandlePos
    public Vector hourHandlePos

    private Clock(CircleDesc circleDesc, Vector clockStart) {
        this.circleDesc = circleDesc
        this.clockStart = clockStart
    }

    static create() {
        def center = Vector.unitVector() * 200.0
        def circleDesc = new CircleDesc(center: center, radius: 100.0)
        def clockStart = CircleOperations.getPointOnCircleInRadians(circleDesc, MathConstants.PI / 2)
        def clock = new Clock(circleDesc, clockStart)
        clock.setRenderComponent(new ClockRenderComponent ())
        return clock
    }

    Vector getCenter() {
        circleDesc.center
    }

    BigDecimal getRadius() {
        circleDesc.radius
    }

    @Override
    void update(Long timestamp, Long delta) {
        def time = Calendar.getInstance()

        secondHandlePos = CircleOperations.getPointOnCircleFromOtherPointInRadians(circleDesc, - SECOND_CIRCLE_STEP * time.get(Calendar.SECOND), clockStart)
        minuteHandlePos = CircleOperations.getPointOnCircleFromOtherPointInRadians(circleDesc, - MINUTE_CIRCLE_STEP * time.get(Calendar.MINUTE), clockStart)
        hourHandlePos = CircleOperations.getPointOnCircleFromOtherPointInRadians(circleDesc, - HOUR_CIRCLE_STEP * time.get(Calendar.HOUR), clockStart)
    }
}
