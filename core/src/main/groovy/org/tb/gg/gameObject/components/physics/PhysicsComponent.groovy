package org.tb.gg.gameObject.components.physics

import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.global.geom.Vector

abstract class PhysicsComponent<T> implements Updateable {
    BaseGameObject parent
    String collisionGroup = 'NONE'
    Set<String> collidesWithGroups = new HashSet<>()

    Vector velocity
    Boolean collides

    @Override
    void update(Long timestamp, Long delta) {
        def body = parent.body
        body.center = body.center + (velocity * BigDecimal.valueOf(delta))
    }
}