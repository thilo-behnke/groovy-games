package org.tb.gg.collision

import groovyjarjarantlr4.v4.runtime.misc.Tuple2
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.physics.CollisionDefinition
import org.tb.gg.gameObject.components.physics.CollisionSettings
import org.tb.gg.gameObject.components.physics.PhysicStats
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.shape.Point
import org.tb.gg.global.geom.Vector
import org.tb.gg.mocks.DummyGameObject
import spock.lang.Specification

class DefaultCollisionDetectorSpec extends Specification {

    DefaultCollisionDetector defaultCollisionHandler

    void setup() {
        defaultCollisionHandler = new DefaultCollisionDetector()
    }

    def 'empty set of game objects'() {
        given:
        def gameObjects = (Set) []
        when:
        def res = defaultCollisionHandler.detect(gameObjects)
        then:
        res == (Set) []
    }

    def 'multiple game objects, but none are colliding'() {
        given:
        def gameObjects = createCollidingGameObjects(5, [])
        when:
        def res = defaultCollisionHandler.detect((Set<BaseGameObject>) gameObjects)
        then:
        res == (Set) []
    }

    def 'multiple game objects, two are colliding'() {
        given:
        def gameObjects = createCollidingGameObjects(5, [new Tuple2<>(1, 2)])
        when:
        def res = defaultCollisionHandler.detect((Set<BaseGameObject>) gameObjects)
        then:
        res == (Set) [new Collision(a: gameObjects[1], b: gameObjects[2], type: CollisionType.OVERLAP)]
    }

    def 'lots of game objects, multiple are colliding'() {
        given:
        def gameObjects = createCollidingGameObjects(50, [new Tuple2<>(10, 2), new Tuple2<Integer, Integer>(30, 4)])
        when:
        def res = defaultCollisionHandler.detect((Set<BaseGameObject>) gameObjects)
        then:
        res == (Set) [
                new Collision(a: gameObjects[2], b: gameObjects[10], type: CollisionType.OVERLAP),
                new Collision(a: gameObjects[4], b: gameObjects[30], type: CollisionType.OVERLAP)
        ]
    }

    private createCollidingGameObjects(Integer nrToCreate, List<Tuple2<Integer, Integer>> collidingPairs) {
        def gameObjects = (0..nrToCreate).collect {
            def mockBody = Mock(ShapeBody)
            mockBody.getStructure() >> new Point(pos: Vector.unitVector())
            return createGameObject(mockBody, it)
        }
        for (def pair in collidingPairs) {
            gameObjects[pair.getItem1()].body.collidesWith(gameObjects[pair.getItem2()].body.getStructure()) >> true
            gameObjects[pair.getItem2()].body.collidesWith(gameObjects[pair.getItem1()].body.getStructure()) >> true
        }
        return gameObjects
    }

    private static createGameObject(ShapeBody shapeBody, Integer id) {
        def obj = DummyGameObject.create(shapeBody)
        obj.physicsComponent = new PhysicsComponent(
                new CollisionSettings(
                        collisionGroup: 'SOME',
                        collidesWithGroups: [new CollisionDefinition(collisionGroup: 'SOME')]
                ),
                new PhysicStats(velocity: Vector.zeroVector())
        )
        obj.id = id
        return obj
    }
}
