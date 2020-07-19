package gameObject.player

import gameObject.GameObject
import global.geom.CircleDesc
import global.geom.CircleOperations
import global.geom.Vector
import global.math.MathConstants

class Clock extends GameObject {
    Vector orientation

    CircleDesc circleDesc
    private final clockStep = Math.PI / 360

    private Long lastUpdate = 0

    private Clock() {}

    static create() {
        def center = Vector.unitVector() * 1000.0
        def circleDesc = new CircleDesc(center: center, radius: 100.0)
        def startPos = CircleOperations.getPointOnCircleInRadians(circleDesc, MathConstants.PI / 2)
        def player = new Clock(position: startPos)
        player.circleDesc = circleDesc
        player.setRenderComponent(new ClockRenderComponent ())
        player.updateOrientation()
        return player
    }

    Vector getCenter() {
        circleDesc.center
    }

    BigDecimal getRadius() {
        circleDesc.radius
    }

    void updateOrientation() {
        orientation = position - this.circleDesc.center
    }

    @Override
    void update(Long timestamp, Long delta) {
        if(timestamp - lastUpdate < 1000) {
            return
        }

        // TODO: This does not work as expected - because Vector is not immutable?
        lastUpdate = timestamp
        position = CircleOperations.getPointOnCircleFromOtherPointInRadians(circleDesc, clockStep, position)
//        def newX = position.x * Math.cos(clockStep) + position.y * Math.sin(clockStep)
//        def newY = - position.x * Math.sin(clockStep) + position.y * Math.cos(clockStep)
//        position = new Vector(x: newX, y: newY).normalize()

        updateOrientation()
    }
}
