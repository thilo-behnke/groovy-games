package org.tb.gg.collision

import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.Direction
import org.tb.gg.global.geom.Vector
import spock.lang.Specification

class CollisionDirectionResolverSpec extends Specification {

    CollisionDirectionResolver collisionDirectionResolver

    void setup() {
        collisionDirectionResolver = new CollisionDirectionResolver()
    }

    def 'detect collision a --> b'() {
        given:
        def collision = createARightBCollision()
        when:
        def directionA = collisionDirectionResolver.resolveCollisionDirection(collision)
        then:
        directionA == Direction.RIGHT
    }

    private static createARightBCollision() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 10, y: 5), new Vector(x: 4, y: 3))))
                .build()
        objA.id = 1

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 11, y: 5), new Vector(x: 5, y: 5))))
                .build()
        objB.id = 2

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID)
    }
}
