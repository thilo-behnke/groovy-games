package org.tb.gg.gameObject.components.physics

class NoopPhysicsComponent extends PhysicsComponent {
    NoopPhysicsComponent() {
        setCollisionGroups((Set<String>) ['NONE'])
    }

    @Override
    void update(Long timestamp, Long delta) {
    }
}
