package gameObject.player

import gameObject.GameObject
import global.geom.Vector

class Player extends GameObject {
    private Player() {}

    static create() {
        def player = new Player(position: new Vector(x: 0, y: 0))
        player.setRenderComponent(new PlayerRenderComponent())
        return player
    }

    @Override
    void update(Long timestamp, Long delta) {
        if (getPosition().x < 100) {
            setPosition(getPosition() + Vector.unitVector())
        }
    }
}
