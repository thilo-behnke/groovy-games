package org.tb.gg.collision.handler

import org.tb.gg.collision.Collision
import org.tb.gg.di.ServiceProvider
import org.tb.gg.gameObject.BaseGameObject
import spock.lang.Specification

class GameObjectCollisionHandlerReferrerSpec extends Specification {

    CollisionHandlerReferrer collisionHandlerReferrer
    CollisionTypeHandler collisionTypeHandler

    ABCollisionHandler collisionHandlerAB
    BACollisionHandler collisionHandlerBA
    CACollisionHandler collisionHandlerCA

    def setup() {
        collisionHandlerReferrer = new CollisionHandlerReferrer()

        collisionTypeHandler = Mock(CollisionTypeHandler)
        ServiceProvider.registerSingletonService(collisionTypeHandler, CollisionTypeHandler.class.getSimpleName())

        collisionHandlerAB = Spy(ABCollisionHandler)
        collisionHandlerBA = Spy(BACollisionHandler)
        collisionHandlerCA = Spy(CACollisionHandler)
    }

    @SuppressWarnings("unused")
    def cleanup() {
        ServiceProvider.reset()
    }

    def 'no collision handlers registered'() {
        when:
        collisionHandlerReferrer.handleCollision(getCollisionAB())
        then:
        0 * collisionHandlerAB.handleCollision(*_)
        0 * collisionHandlerBA.handleCollision(*_)
        0 * collisionHandlerCA.handleCollision(*_)
        1 * collisionTypeHandler.handleCollisionByType(*_)
    }

    def 'no fitting collision handler for collision'() {
        given:
        def collision = getCollisionBC()
        registerCollisionHandlers()
        when:
        collisionHandlerReferrer.handleCollision(collision)
        then:
        1 * collisionHandlerAB.handleCollision(collision.a, collision.b)
        0 * collisionHandlerAB.handleCollisionHook(collision.a, collision.b)
        then:
        1 * collisionHandlerBA.handleCollision(collision.a, collision.b)
        0 * collisionHandlerBA.handleCollisionHook(collision.a, collision.b)
        then:
        1 * collisionHandlerCA.handleCollision(collision.a, collision.b)
        0 * collisionHandlerCA.handleCollisionHook(collision.a, collision.b)
        then:
        1 * collisionTypeHandler.handleCollisionByType(collision)
    }

    def 'trigger fitting collision handlers for collision (multiple registered)'() {
        given:
        def collision = getCollisionAB()
        registerCollisionHandlers()
        when:
        collisionHandlerReferrer.handleCollision(collision)
        then:
        1 * collisionHandlerAB.handleCollision(collision.a, collision.b)
        1 * collisionHandlerAB.handleCollisionHook(collision.a, collision.b)
        then:
        1 * collisionHandlerBA.handleCollision(collision.a, collision.b)
        1 * collisionHandlerBA.handleCollisionHook(collision.b, collision.a)
        then:
        1 * collisionHandlerCA.handleCollision(collision.a, collision.b)
        0 * collisionHandlerCA.handleCollisionHook(collision.a, collision.b)
        then:
        1 * collisionTypeHandler.handleCollisionByType(collision)
    }

    private static getCollisionAB() {
        new Collision(a: new GameObjectA(), b: new GameObjectB())
    }

    private static getCollisionBC() {
        new Collision(a: new GameObjectB(), b: new GameObjectC())
    }

    private registerCollisionHandlers() {
        [collisionHandlerAB, collisionHandlerBA, collisionHandlerCA].each {
            ServiceProvider.registerMultiInstanceService(it, 'GameObjectCollisionHandler')
        }
    }
}

class ABCollisionHandler extends GameObjectCollisionHandler<GameObjectA, GameObjectB> {
    @Override
    void handleCollision(GameObjectA a, GameObjectB b) {
        handleCollisionHook(a, b)
    }

    @SuppressWarnings("unused")
    void handleCollisionHook(GameObjectA a, GameObjectB b) {
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}

class BACollisionHandler extends GameObjectCollisionHandler<GameObjectB, GameObjectA> {
    @Override
    void handleCollision(GameObjectB a, GameObjectA b) {
        handleCollisionHook(a, b)
    }

    @SuppressWarnings("unused")
    void handleCollisionHook(GameObjectB a, GameObjectA b) {
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}

class CACollisionHandler extends GameObjectCollisionHandler<GameObjectC, GameObjectA> {

    @Override
    void handleCollision(GameObjectC a, GameObjectA b) {
        handleCollisionHook(a, b)
    }

    @SuppressWarnings("unused")
    void handleCollisionHook(GameObjectC a, GameObjectA b) {
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
