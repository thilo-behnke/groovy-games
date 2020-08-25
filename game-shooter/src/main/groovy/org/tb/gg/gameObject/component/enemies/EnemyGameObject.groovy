package org.tb.gg.gameObject.component.enemies

import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector

class EnemyGameObject extends BaseGameObject {
    @Delegate EnemyProperties enemyProperties = new EnemyProperties()

    boolean wasHitRecently = false

    static EnemyGameObject create(Vector pos) {
        def physicsComp = OneHitEnemyPhysicsComponent.create(Vector.zeroVector())
        def bullet = (EnemyGameObject) new GameObjectBuilder<>(EnemyGameObject)
                .setBody(new ShapeBody(new Rect(pos, new Vector(x: 20, y: 20))))
                .setRenderComponent(new EnemyRenderComponent())
                .setPhysicsComponent(physicsComp)
                .build()
        bullet.setOrientation(Vector.zeroVector())
        return bullet
    }

    @Override
    void update(Long timestamp, Long delta) {
        super.update(timestamp, delta)

        wasHitRecently = false
        physicsComponent.update(timestamp, delta)
    }
}

class EnemyProperties {
    int hp
    int score

    void setHp(int hp) {
        this.hp = Math.max(0, hp)
    }
}