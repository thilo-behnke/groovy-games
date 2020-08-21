package org.tb.gg.gameObject.components.physics

import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.global.geom.Vector

class PhysicsComponent<C extends CollisionSettings, S extends PhysicStats> implements Updateable {
    C collisionSettings
    S physicStats

    BaseGameObject parent
    Boolean collides

    PhysicsComponent(CollisionSettings collisionSettings = NoopCollisionSettings.get(), PhysicStats physicStats) {
        this.collisionSettings = collisionSettings as C
        this.physicStats = physicStats as S
    }

    @Override
    void update(Long timestamp, Long delta) {
        def body = parent.body
        body.center = body.center + (physicStats.velocity * BigDecimal.valueOf(delta))
    }

    boolean collidesWith(PhysicsComponent physicsComponent) {
        collisionSettings.collidesWithGroups.contains(physicsComponent.collisionSettings.collisionGroup)
        || physicsComponent.collisionSettings.collidesWithGroups.contains(collisionSettings.collisionGroup)
    }
}

class CollisionSettings {
    String collisionGroup
    Set<String> collidesWithGroups
}

class NoopCollisionSettings extends CollisionSettings {
    private static NoopCollisionSettings noop

    private NoopCollisionSettings() {
        setCollisionGroup('NONE')
        setCollidesWithGroups(new HashSet<String>())
    }

    static NoopCollisionSettings get() {
        if (!noop) {
            noop = new NoopCollisionSettings()
        }
        return noop
    }
}

class PhysicStats {
    Vector velocity
}
