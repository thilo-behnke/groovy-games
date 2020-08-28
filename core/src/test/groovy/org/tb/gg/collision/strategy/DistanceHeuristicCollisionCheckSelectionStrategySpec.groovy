package org.tb.gg.collision.strategy

import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import spock.lang.Specification

class DistanceHeuristicCollisionCheckSelectionStrategySpec extends Specification {
    DistanceHeuristicCollisionCheckSelectionStrategy distanceHeuristicCollisionCheckSelectionStrategy

    void setup() {
        distanceHeuristicCollisionCheckSelectionStrategy = new DistanceHeuristicCollisionCheckSelectionStrategy()
    }

    def 'should return empty list for empty provided list of game objects'() {
        given:
        def gameObjects = []
        when:
        def res = distanceHeuristicCollisionCheckSelectionStrategy.selectPotentialCollisions(gameObjects)
        then:
        res == []
    }

    def 'should filter out game objects that are too far away from each other'() {
        given:
        def gameObjects = create2GameObjectsFarAwayFromEachOther()
        when:
        def res = distanceHeuristicCollisionCheckSelectionStrategy.selectPotentialCollisions(gameObjects)
        then:
        res == []
    }

    def 'should return game objects if their distance is within their diagonals sum'() {
        given:
        def gameObjects = create2GameObjectsCloseTogether()
        when:
        def res = distanceHeuristicCollisionCheckSelectionStrategy.selectPotentialCollisions(gameObjects)
        then:
        res == [gameObjects]
    }

    def 'should ignore game objects that do not have a body'() {
        given:
        def gameObjects = create2GameObjectsWithoutBodies()
        when:
        def res = distanceHeuristicCollisionCheckSelectionStrategy.selectPotentialCollisions(gameObjects)
        then:
        res == []
    }

    private static create2GameObjectsFarAwayFromEachOther() {
        return [
                new GameObjectBuilder<>(BaseGameObject).setBody(new ShapeBody(new Rect(Vector.unitVector(), Vector.unitVector()))).build(),
                new GameObjectBuilder<>(BaseGameObject).setBody(new ShapeBody(new Rect(Vector.unitVector() * 30.0, Vector.unitVector()))).build()
        ]
    }

    private static create2GameObjectsCloseTogether() {
        return [
                new GameObjectBuilder<>(BaseGameObject).setBody(new ShapeBody(new Rect(Vector.unitVector(), Vector.unitVector() * 2.0))).build(),
                new GameObjectBuilder<>(BaseGameObject).setBody(new ShapeBody(new Rect(Vector.unitVector() * 2.0, Vector.unitVector()))).build()
        ]
    }

    private static create2GameObjectsWithoutBodies() {
        return [
                new GameObjectBuilder<>(BaseGameObject).build(),
                new GameObjectBuilder<>(BaseGameObject).build()
        ]
    }
}
