package gameObject.player

import gameObject.GameObject
import global.geom.CircleDesc
import global.geom.CircleOperations
import global.geom.Vector
import global.math.MathConstants

class Clock extends GameObject {
    private final SECOND_CIRCLE_STEP = MathConstants.PI * 2 / 60
    private final MINUTE_CIRCLE_STEP = MathConstants.PI * 2 / 60
    private final HOUR_CIRCLE_STEP = MathConstants.PI * 2 / 24

    private CircleDesc circleDesc
    private Vector clockStart

    private Long hours
    private Long minutes
    private Long seconds
    private Long lastUpdate = 0


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
        // TODO: Start the handle at 0 / 12.
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
        if(timestamp - lastUpdate < 1000) {
            return
        }

        // TODO: This does not work as expected - because Vector is not immutable?
        lastUpdate = timestamp
        def time = Calendar.getInstance()

        secondHandlePos = CircleOperations.getPointOnCircleFromOtherPointInRadians(circleDesc, - SECOND_CIRCLE_STEP * time.get(Calendar.SECOND), clockStart)
        minuteHandlePos = CircleOperations.getPointOnCircleFromOtherPointInRadians(circleDesc, - MINUTE_CIRCLE_STEP * time.get(Calendar.MINUTE), clockStart)
        hourHandlePos = CircleOperations.getPointOnCircleFromOtherPointInRadians(circleDesc, - HOUR_CIRCLE_STEP * time.get(Calendar.HOUR), clockStart)
    }
}
