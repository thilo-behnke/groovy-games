package org.tb.gg.gameObject.component.enemies

import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.gameObject.components.body.SpriteBodyFactory
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.global.geom.Vector
import org.tb.gg.resources.ShooterGameResource

class EnemyGameObject extends BaseGameObject {
    @Delegate EnemyProperties enemyProperties = new EnemyProperties()

    boolean wasHitRecently = false

    static EnemyGameObject create(Vector pos) {
        def physicsComp = OneHitEnemyPhysicsComponent.create(Vector.zeroVector())
        def bullet = (EnemyGameObject) new GameObjectBuilder<>(EnemyGameObject)
                .setBody(new SpriteBodyFactory().fromResource(ShooterGameResource.SPACESHIP_BLUE.name()))
                .setRenderComponent(new EnemyRenderComponent())
                .setPhysicsComponent(physicsComp)
                .build()
        bullet.setInputComponent(EnemyInputComponent.create(bullet))
        bullet.setOrientation(Vector.zeroVector())
        return bullet
    }

    @Override
    void update(Long timestamp, Long delta) {
        super.update(timestamp, delta)

        updateVelocity(inputComponent.activeActions)

        wasHitRecently = false
        physicsComponent.update(timestamp, delta)
    }

    // TODO: Copy and pasted - refactor.
    private updateVelocity(Set<String> activeActions) {
        def newX = 0
        def newY = 0
        // Update X.
        if (activeActions.contains(EnemyAction.RIGHT.toString())) {
            newX = 1
        } else if (activeActions.contains(EnemyAction.LEFT.toString())) {
            newX = -1
        }
        // Update Y.
        if (activeActions.contains(EnemyAction.UP.toString())) {
            newY = 1
        } else if (activeActions.contains(EnemyAction.DOWN.toString())) {
            newY = -1
        }
        physicsComponent.setVelocity(new Vector(x: newX, y: newY))
    }

}

class EnemyProperties {
    int hp
    int score

    void setHp(int hp) {
        this.hp = Math.max(0, hp)
    }
}