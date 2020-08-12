package org.tb.gg.gameObject.components.physics

import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.GameObject
import org.tb.gg.global.geom.Vector

abstract class PhysicsComponent<T> implements Updateable {
    GameObject parent
    Boolean collides

    Vector velocity

    abstract boolean shouldCollide()

    @Override
    void update(Long timestamp, Long delta) {
        def body = parent.body
        body.center = body.center + (velocity * BigDecimal.valueOf(delta))
    }
}