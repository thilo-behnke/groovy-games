package gameObject.player

import gameObject.GameObject
import global.geom.Vector

class Clock extends GameObject {
    Vector center
    Vector startPos
    Vector orientation

    private final clockStep = Math.PI / 360

    private Long lastUpdate = 0

    private Clock() {}

    static create() {
        def startPos = Vector.unitVector() * 1000
        def player = new Clock(position: startPos)
        player.startPos = startPos
        player.setRenderComponent(new ClockRenderComponent ())
        player.setCenter(startPos + new Vector(x: 0, y: 100))
        player.updateOrientation()
        return player
    }

    void setCenter(Vector center) {
        this.center = center
    }

    void updateOrientation() {
        orientation = position - this.center
    }

    @Override
    void update(Long timestamp, Long delta) {
        if(timestamp - lastUpdate < 1000) {
            return
        }

        lastUpdate = timestamp
        def newX = position.x * Math.cos(clockStep) + position.y * Math.sin(clockStep)
        def newY = - position.x * Math.sin(clockStep) + position.y * Math.cos(clockStep)
        position = new Vector(x: newX, y: newY)
        updateOrientation()
    }
}
