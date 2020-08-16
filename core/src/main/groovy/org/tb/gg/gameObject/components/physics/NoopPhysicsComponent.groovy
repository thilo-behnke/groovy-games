package org.tb.gg.gameObject.components.physics

class NoopPhysicsComponent extends PhysicsComponent {
    NoopPhysicsComponent() {
        setCollidesWithGroups((Set<String>) ['NONE'])
    }

    @Override
    void update(Long timestamp, Long delta) {
    }
}
