package org.tb.gg.gameObject.components.physics

import org.tb.gg.global.geom.Vector

class NoopPhysicsComponent extends PhysicsComponent {
    private static NoopPhysicsComponent noop

    private NoopPhysicsComponent(collisionSettings, physicStats) {
        super()
        setCollisionSettings(collisionSettings)
        setPhysicStats(physicStats)
    }

    static NoopPhysicsComponent get() {
        if (!noop) {
            noop = new NoopPhysicsComponent(NoopCollisionSettings.get(), new PhysicStats(velocity: Vector.zeroVector()))
        }
        return noop
    }

    @Override
    void update(Long timestamp, Long delta) {
    }
}
