package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.collision.CollisionType
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.global.geom.Vector
import spock.lang.Specification

class DefaultCollisionTypeHandlerSpec extends Specification {
    private DefaultCollisionTypeHandler defaultCollisionTypeHandler

    def setup() {
        defaultCollisionTypeHandler = new DefaultCollisionTypeHandler()
    }

    def 'do nothing for collision type overlap'() {
        given:
        def gameObjectA = createGameObject()
        def gameObjectB = createGameObject()
        def collision = new Collision(a: gameObjectA, b: gameObjectB, type: CollisionType.OVERLAP)
        when:
        defaultCollisionTypeHandler.handleCollisionByType(collision)
        then:
        0 * gameObjectA.body.setCenter(*_)
        0 * gameObjectB.body.setCenter(*_)
    }

    def 'reposition the element with higher velocity (a) in case of solid collision type'() {
        given:
        def ( gameObjectA, gameObjectB ) = create2GameObjectsWithABeingFaster()
        def collision = new Collision(a: gameObjectA, b: gameObjectB, type: CollisionType.SOLID)
        when:
        defaultCollisionTypeHandler.handleCollisionByType(collision)
        then:
        1 * gameObjectA.body.setCenter(*_)
        0 * gameObjectB.body.setCenter(*_)
    }

    def 'reposition the element with higher velocity (b) in case of solid collision type'() {
        given:
        def ( gameObjectA, gameObjectB ) = create2GameObjectsWithBBeingFaster()
        def collision = new Collision(a: gameObjectA, b: gameObjectB, type: CollisionType.SOLID)
        when:
        defaultCollisionTypeHandler.handleCollisionByType(collision)
        then:
        0 * gameObjectA.body.setCenter(*_)
        1 * gameObjectB.body.setCenter(*_)
    }

    private create2GameObjectsWithABeingFaster() {
        create2GameObjects(new Vector(x: 10, y: 10), new Vector(x: 5, y: -2))
    }

    private create2GameObjectsWithBBeingFaster() {
        create2GameObjects(new Vector(x: 5, y: -2), new Vector(x: 10, y: 10))
    }

    private create2GameObjects(Vector velocityA, Vector velocityB) {
        def gameObjectA = createGameObject()
        gameObjectA.physicsComponent.getVelocity() >> velocityA
        gameObjectA.body.getCenter() >> Vector.unitVector()
        gameObjectA.id = 1
        def gameObjectB = createGameObject()
        gameObjectB.physicsComponent.getVelocity() >> velocityB
        gameObjectB.body.getCenter() >> Vector.unitVector()
        gameObjectB.id = 2

        return [gameObjectA, gameObjectB]
    }


    private createGameObject() {
        new GameObjectBuilder(SomeGameObject)
                .setPhysicsComponent(Mock(PhysicsComponent))
                .setBody(Mock(ShapeBody))
                .build()
    }
}

class SomeGameObject extends BaseGameObject {

}
