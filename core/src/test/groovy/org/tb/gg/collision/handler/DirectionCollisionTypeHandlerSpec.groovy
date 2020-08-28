package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.collision.CollisionType
import org.tb.gg.collision.ShapeCollisionDetector
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.physics.PhysicStats
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.Direction
import org.tb.gg.global.geom.Vector
import spock.lang.Specification

class DirectionCollisionTypeHandlerSpec extends Specification {

    DirectionCollisionTypeHandler directionCollisionTypeHandler
    ShapeCollisionDetector shapeCollisionDetector

    def setup() {
        directionCollisionTypeHandler = new DirectionCollisionTypeHandler()
        shapeCollisionDetector = new ShapeCollisionDetector()
    }

    private 'objA-->objB: Move A slightly to left of B'() {
        given:
        def collision = createCollisionARight()
        def positionBBefore = new Vector(x: collision.b.body.center.x, y: collision.b.body.center.y)
        when:
        directionCollisionTypeHandler.handleCollisionByType(collision)
        then:
        collision.a.body.center == new Vector(x: 11.5, y: 7.5)
        collision.b.body.center == positionBBefore
        shouldNotCollideAnymore(collision)
    }

    private 'objA<--objB: Move A slightly to left of B'() {
        given:
        def collision = createCollisionALeft()
        def positionBBefore = new Vector(x: collision.b.body.center.x, y: collision.b.body.center.y)
        when:
        directionCollisionTypeHandler.handleCollisionByType(collision)
        then:
        collision.a.body.center == new Vector(x: 19.0, y: 5.0)
        collision.b.body.center == positionBBefore
        shouldNotCollideAnymore(collision)
    }

    private 'objB>>objA: Move B slightly to a position above of A'() {
        given:
        def collision = createCollisionBBottom()
        def positionABefore = new Vector(x: collision.a.body.center.x, y: collision.a.body.center.y)
        when:
        directionCollisionTypeHandler.handleCollisionByType(collision)
        then:
        collision.b.body.center == new Vector(x: 10.5, y: 15.5)
        collision.a.body.center == positionABefore
        shouldNotCollideAnymore(collision)
    }

    private 'objB<<objA: Move B slightly to a position below of A'() {
        given:
        def collision = createCollisionBUp()
        def positionABefore = new Vector(x: collision.a.body.center.x, y: collision.a.body.center.y)
        when:
        directionCollisionTypeHandler.handleCollisionByType(collision)
        then:
        collision.b.body.center == new Vector(x: 6.5, y: 2.5)
        collision.a.body.center == positionABefore
        shouldNotCollideAnymore(collision)
    }

    private static createCollisionARight() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 11, y: 10), new Vector(x: 5, y: 5))))
                .setPhysicsComponent(new PhysicsComponent(physicStats: new PhysicStats(velocity: new Vector(x: 1, y: 0))))
                .build()
        objA.id = 1

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 15, y: 10), new Vector(x: 5, y: 5))))
                .setPhysicsComponent(new PhysicsComponent(physicStats: new PhysicStats(velocity: new Vector(x: 0, y: 0))))
                .build()
        objB.id = 2

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID, directionA: Direction.RIGHT, directionB: Direction.LEFT)
    }

    private static createCollisionALeft() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 10, y: 10), new Vector(x: 10, y: 10))))
                .setPhysicsComponent(new PhysicsComponent(physicStats: new PhysicStats(velocity: new Vector(x: -1, y: 5))))
                .build()
        objA.id = 1

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 8, y: 10), new Vector(x: 5, y: 5))))
                .setPhysicsComponent(new PhysicsComponent(physicStats: new PhysicStats(velocity: new Vector(x: -0.5, y: 4))))
                .build()
        objB.id = 2

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID, directionA: Direction.LEFT, directionB: Direction.RIGHT)
    }

    private static createCollisionBBottom() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 10, y: 12), new Vector(x: 4, y: 3))))
                .setPhysicsComponent(new PhysicsComponent(physicStats: new PhysicStats(velocity: new Vector(x: 1, y: 4))))
                .build()
        objA.id = 1

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 8, y: 10), new Vector(x: 5, y: 5))))
                .setPhysicsComponent(new PhysicsComponent(physicStats: new PhysicStats(velocity: new Vector(x: 3, y: 8))))
                .build()
        objB.id = 2

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID, directionA: Direction.UP, directionB: Direction.DOWN)
    }

    private static createCollisionBUp() {
        def objA = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 10, y: 8), new Vector(x: 4, y: 3))))
                .setPhysicsComponent(new PhysicsComponent(physicStats: new PhysicStats(velocity: new Vector(x: 1, y: 4))))
                .build()
        objA.id = 1

        def objB = new GameObjectBuilder(BaseGameObject)
                .setBody(new ShapeBody(new Rect(new Vector(x: 5, y: 10), new Vector(x: 3, y: 3))))
                .setPhysicsComponent(new PhysicsComponent(physicStats: new PhysicStats(velocity: new Vector(x: 3, y: 8))))
                .build()
        objB.id = 2

        return new Collision(a: objA, b: objB, type: CollisionType.SOLID, directionA: Direction.DOWN, directionB: Direction.UP)
    }

    private shouldNotCollideAnymore(Collision collision) {
        !shapeCollisionDetector.detect(collision.a.body.shape, collision.b.body.shape)
    }

}
