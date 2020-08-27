package org.tb.gg.collision

import org.tb.gg.di.ServiceProvider
import org.tb.gg.engine.framecache.FrameCache
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.Direction
import org.tb.gg.global.geom.Vector
import spock.lang.Specification

class CollisionDirectionResolverSpec extends Specification {

    CollisionDirectionResolver collisionDirectionResolver
    FrameCache frameCache

    void setup() {
        collisionDirectionResolver = new CollisionDirectionResolver()

        frameCache = Mock(FrameCache)
        ServiceProvider.registerSingletonService(frameCache, FrameCache.class.simpleName)
    }

    def 'detect collision a --> b'() {
        given:
        def collision = createARightBCollision()
        when:
        def directionA = collisionDirectionResolver.resolveCollisionDirection(collision)
        then:
        directionA == Direction.RIGHT
    }

    def 'detect collision a <-- b'() {
        given:
        def collision = createBRightACollision()
        when:
        def directionA = collisionDirectionResolver.resolveCollisionDirection(collision)
        then:
        directionA == Direction.LEFT
    }

    def 'detect collision a >> b'() {
        given:
        def collision = createADownBCollision()
        when:
        def directionA = collisionDirectionResolver.resolveCollisionDirection(collision)
        then:
        directionA == Direction.DOWN
    }

    def 'detect collision a << b'() {
        given:
        def collision = createAUpBCollision()
        when:
        def directionA = collisionDirectionResolver.resolveCollisionDirection(collision)
        then:
        directionA == Direction.UP
    }

    private static createARightBCollision() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 5, y: 5), new Vector(x: 4, y: 3))))
                .build()
        objA.id = 1
        objA.body.shape.center = objA.body.shape.center + new Vector(x: 5, y: 0)

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 11, y: 5), new Vector(x: 5, y: 5))))
                .build()
        objB.id = 2

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID)
    }

    private static createBRightACollision() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 18, y: 5), new Vector(x: 4, y: 3))))
                .build()
        objA.id = 1
        objA.body.shape.center = objA.body.shape.center + new Vector(x: -5, y: 0)

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 11, y: 5), new Vector(x: 5, y: 5))))
                .build()
        objB.id = 2

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID)
    }

    private static createADownBCollision() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 5, y: 14), new Vector(x: 4, y: 3))))
                .build()
        objA.id = 1
        objA.body.shape.center = objA.body.shape.center + new Vector(x: 0, y: -5)

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 5, y: 10), new Vector(x: 5, y: 5))))
                .build()
        objB.id = 2

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID)
    }

    private static createAUpBCollision() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 5, y: 4), new Vector(x: 4, y: 3))))
                .build()
        objA.id = 1
        objA.body.shape.center = objA.body.shape.center + new Vector(x: 0, y: 4)

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 5, y: 10), new Vector(x: 5, y: 5))))
                .build()
        objB.id = 2

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID)
    }
}
