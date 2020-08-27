package org.tb.gg.gameObject.player

import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.global.geom.CircleDesc
import org.tb.gg.global.geom.CircleOperations
import org.tb.gg.global.geom.Vector
import org.tb.gg.global.math.MathConstants

class Clock extends BaseGameObject {
    final SECOND_CIRCLE_STEP = MathConstants.PI * 2 / 60
    final MINUTE_CIRCLE_STEP = MathConstants.PI * 2 / 60
    final HOUR_CIRCLE_STEP = MathConstants.PI * 2 / 12

    Vector clockStart

    public Vector secondHandlePos
    public Vector minuteHandlePos
    public Vector hourHandlePos

    private Clock(Vector clockStart) {
        this.clockStart = clockStart
    }

    static create() {
        def center = Vector.unitVector() * 200.0
        def radius = 100.0
        def clockStart = new Vector(x: 0, y: 1).scale(radius)
        def clock = new Clock(clockStart)

        clock.setBody(new ShapeBody(new Circle(center: center, radius: radius)))
        clock.setRenderComponent(new ClockRenderComponent())
        clock.setInputComponent(NoopInputComponent.get())
        return clock
    }

    Vector getCenter() {
        body.center
    }

    BigDecimal getRadius() {
        ((Circle) body.shape).radius
    }

    @Override
    void update(Long timestamp, Long delta) {
        def time = Calendar.getInstance()

        secondHandlePos = clockStart.rotate(SECOND_CIRCLE_STEP * time.get(Calendar.SECOND))
        minuteHandlePos = clockStart.rotate(MINUTE_CIRCLE_STEP * time.get(Calendar.MINUTE))
        hourHandlePos = clockStart.rotate(HOUR_CIRCLE_STEP * time.get(Calendar.HOUR))
    }
}
