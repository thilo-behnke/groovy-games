package org.tb.gg.gameObject.component.enemies

import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.gameObject.components.body.SpriteBodyFactory
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.global.geom.Vector
import org.tb.gg.resources.ShooterGameResource
import org.tb.gg.utils.MathUtils

class EnemyGameObject extends BaseGameObject {
    @Delegate
    EnemyProperties enemyProperties = new EnemyProperties()

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

        if (orientation != Vector.zeroVector()) {
            body.shape.setRotation(new Vector(x: 1, y: 0).angleBetween(orientation))
        } else {
            body.shape.setRotation(0.0)
        }

        wasHitRecently = false
        physicsComponent.update(timestamp, delta)
    }

    // TODO: Copy and pasted - refactor.
    private updateVelocity(Set<String> activeActions) {
        def maxVelocity = 0.5
        def newX = physicsComponent.velocity.x
        def newY = physicsComponent.velocity.y
        // Update X.
        if (activeActions.contains(EnemyAction.RIGHT.toString())) {
            newX = newX + 0.05
        } else if (activeActions.contains(EnemyAction.LEFT.toString())) {
            newX = newY - 0.05
        }
        // Update Y.
        if (activeActions.contains(EnemyAction.UP.toString())) {
            newY = newY + 0.05
        } else if (activeActions.contains(EnemyAction.DOWN.toString())) {
            newY = newY - 0.05
        }
        newX = MathUtils.normalizeInRange(newX, -maxVelocity, +maxVelocity)
        newY = MathUtils.normalizeInRange(newY, -maxVelocity, +maxVelocity)
        def velocity = new Vector(x: newX, y: newY)

        physicsComponent.setVelocity(velocity)
        orientation = velocity.normalize()
    }
}

class EnemyProperties {
    int hp
    int score

    void setHp(int hp) {
        this.hp = Math.max(0, hp)
    }
}