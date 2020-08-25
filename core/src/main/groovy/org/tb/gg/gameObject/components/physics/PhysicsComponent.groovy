package org.tb.gg.gameObject.components.physics

import org.tb.gg.collision.CollisionType
import org.tb.gg.collision.CollisionDirectionRegistry
import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.global.geom.Vector

class PhysicsComponent<C extends CollisionSettings, S extends PhysicStats> implements Updateable {
    C collisionSettings
    @Delegate
    S physicStats

    BaseGameObject parent
    Boolean collides
    CollisionDirectionRegistry collisions = new CollisionDirectionRegistry()

    Vector previousCenter

    @Override
    void update(Long timestamp, Long delta) {
        def body = parent.body
        previousCenter = body.center
        body.center = body.center + (physicStats.velocity * BigDecimal.valueOf(delta))
    }

    boolean shouldCollideWith(PhysicsComponent physicsComponent) {
        collisionSettings.collidesWithGroups.collect { it.collisionGroup }.contains(physicsComponent.collisionSettings.collisionGroup)
                || physicsComponent.collisionSettings.collidesWithGroups.collect { it.collisionGroup }.contains(collisionSettings.collisionGroup)
    }

    CollisionType getCollisionType(PhysicsComponent physicsComponent) {
        def matchingCollisionSettings = collisionSettings.collidesWithGroups.findAll { CollisionDefinition collisionDefinition ->
            physicsComponent.collisionSettings.collisionGroup == collisionDefinition.collisionGroup
        } + physicsComponent.collisionSettings.collidesWithGroups.findAll { CollisionDefinition collisionDefinition ->
            collisionSettings.collisionGroup == collisionDefinition.collisionGroup
        }
        CollisionType.getHighestPrecedence(*matchingCollisionSettings.collect { it.collisionType })
    }
}

class CollisionSettings {
    String collisionGroup
    Set<CollisionDefinition> collidesWithGroups
}

class CollisionDefinition {
    String collisionGroup
    CollisionType collisionType = CollisionType.OVERLAP
}

class NoopCollisionSettings extends CollisionSettings {
    private static NoopCollisionSettings noop

    private NoopCollisionSettings() {
        setCollisionGroup('NONE')
        setCollidesWithGroups(new HashSet<CollisionDefinition>())
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
