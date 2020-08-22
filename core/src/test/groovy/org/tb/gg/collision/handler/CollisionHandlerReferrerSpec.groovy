package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.di.ServiceProvider
import org.tb.gg.gameObject.BaseGameObject
import spock.lang.Specification

class CollisionHandlerReferrerSpec extends Specification {

    CollisionHandlerReferrer collisionHandlerReferrer

    ABCollisionHandler collisionHandlerAB
    BACollisionHandler collisionHandlerBA
    CACollisionHandler collisionHandlerCA

    def setup() {
        collisionHandlerReferrer = new CollisionHandlerReferrer()

        collisionHandlerAB = Spy(ABCollisionHandler)
        collisionHandlerBA = Spy(BACollisionHandler)
        collisionHandlerCA = Spy(CACollisionHandler)
    }

    def cleanup() {
        ServiceProvider.reset()
    }

    def 'no collision handlers registered'() {
        when:
        collisionHandlerReferrer.handleCollision(getCollisionAB())
        then:
        0 * collisionHandlerAB.handleCollision()
        0 * collisionHandlerBA.handleCollision()
        0 * collisionHandlerCA.handleCollision()
    }

    def 'no fitting collision handler for collision'() {
        given:
        def collision = getCollisionBC()
        registerCollisionHandlers()
        when:
        collisionHandlerReferrer.handleCollision(collision)
        then:
        0 * collisionHandlerAB.handleCollision()
        0 * collisionHandlerBA.handleCollision()
        0 * collisionHandlerCA.handleCollision()
    }

    def 'trigger fitting collision handlers for collision (multiple registered)'() {
        given:
        def collision = getCollisionAB()
        registerCollisionHandlers()
        when:
        collisionHandlerReferrer.handleCollision(collision)
        then:
        1 * collisionHandlerAB.handleCollision()
        1 * collisionHandlerBA.handleCollision()
        0 * collisionHandlerCA.handleCollision()
    }

    private getCollisionAB() {
        new Collision(a: new GameObjectA(), b: new GameObjectB())
    }

    private getCollisionBC() {
        new Collision(a: new GameObjectB(), b: new GameObjectC())
    }

    private registerCollisionHandlers() {
        [collisionHandlerAB, collisionHandlerBA, collisionHandlerCA].each {
            ServiceProvider.registerMultiInstanceService(it, 'CollisionHandler')
        }
    }
}

class ABCollisionHandler implements CollisionHandler<GameObjectA, GameObjectB> {
    @Override
    void handleCollision(GameObjectA a, GameObjectB b) {

    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}

class BACollisionHandler implements CollisionHandler<GameObjectB, GameObjectA> {
    @Override
    void handleCollision(GameObjectB a, GameObjectA b) {

    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}

class CACollisionHandler implements CollisionHandler<GameObjectC, GameObjectA> {
    @Override
    void handleCollision(GameObjectC a, GameObjectA b) {

    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}

class GameObjectA extends BaseGameObject {}
class GameObjectB extends BaseGameObject {}
class GameObjectC extends BaseGameObject {}