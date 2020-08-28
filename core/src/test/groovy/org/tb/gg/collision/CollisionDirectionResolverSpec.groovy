package org.tb.gg.collision

import org.tb.gg.di.ServiceProvider
import org.tb.gg.engine.framecache.FrameCache
import org.tb.gg.engine.framecache.FrameState
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Shape
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

    void cleanup() {
        ServiceProvider.reset()
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

    def 'undefined collision (objects are not moving and were colliding in last frame)'() {
        given:
        def collision = createUndefinedCollision()
        when:
        def directionA = collisionDirectionResolver.resolveCollisionDirection(collision)
        then:
        directionA == Direction.UNDEFINED
    }

    private createARightBCollision() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 10, y: 5), new Vector(x: 4, y: 3))))
                .build()
        objA.id = 1

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 11, y: 5), new Vector(x: 5, y: 5))))
                .build()
        objB.id = 2

        buildFrameCache(
                objA, objB, new Rect(new Vector(x: 5, y: 5), new Vector(x: 4, y: 3))
        )

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID)
    }

    private createBRightACollision() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 13, y: 5), new Vector(x: 4, y: 3))))
                .build()
        objA.id = 1

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 11, y: 5), new Vector(x: 5, y: 5))))
                .build()
        objB.id = 2

        buildFrameCache(objA, objB, new Rect(new Vector(x: 18, y: 5), new Vector(x: 4, y: 3)))

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID)
    }

    private createADownBCollision() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 5, y: 9), new Vector(x: 4, y: 3))))
                .build()
        objA.id = 1

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 5, y: 10), new Vector(x: 5, y: 5))))
                .build()
        objB.id = 2

        buildFrameCache(objA, objB, new Rect(new Vector(x: 5, y: 14), new Vector(x: 4, y: 3)))

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID)
    }

    private createAUpBCollision() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 5, y: 8), new Vector(x: 4, y: 3))))
                .build()
        objA.id = 1

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 5, y: 10), new Vector(x: 5, y: 5))))
                .build()
        objB.id = 2

        buildFrameCache(objA, objB, new Rect(new Vector(x: 5, y: 4), new Vector(x: 4, y: 3)))

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID)
    }

    private createUndefinedCollision() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 4, y: 11), new Vector(x: 4, y: 3))))
                .build()
        objA.id = 1

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 5, y: 10), new Vector(x: 5, y: 5))))
                .build()
        objB.id = 2

        buildFrameCache(objA, objB)

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID)
    }

    private buildFrameCache(GameObject a, GameObject b, Shape overrideShapeA = null, Shape overrideShapeB = null) {
        frameCache.getLastFrames(1) >> [
                new FrameState(gameObjectShapeCache: [
                        (a.id): overrideShapeA ?: a.body.shape,
                        (b.id): overrideShapeB ?: b.body.shape,
                ])
        ]
    }
}
