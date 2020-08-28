package org.tb.gg.engine.framecache

import org.tb.gg.di.ServiceProvider
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.gameObject.components.body.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Point
import org.tb.gg.gameObject.shape.Shape
import org.tb.gg.global.geom.Vector
import spock.lang.Specification
import spock.lang.Unroll

class FixedSizeFrameCacheSpec extends Specification {
    private FixedSizeFrameCache fixedSizeFrameCache
    private GameObjectProvider gameObjectProvider

    private List<GameObject> gameObjects

    def setup() {
        fixedSizeFrameCache = new FixedSizeFrameCache()

        gameObjectProvider = Mock(GameObjectProvider)
        ServiceProvider.registerSingletonService(gameObjectProvider, GameObjectProvider.class.getSimpleName())
    }

    def cleanup() {
        ServiceProvider.reset()
    }

    @Unroll
    def 'update frame cache for #nrOfGameObjects game objects'() {
        given:
        createGameObjects(nrOfGameObjects)
        fixedSizeFrameCache.update()
        expect:
        frameCacheToBeUpdatedCorrectly()
        where:
        nrOfGameObjects | _
        1               | _
        11              | _
        100             | _
    }

    @Unroll
    def 'get last frames'() {
        given:
        createGameObjects(nrOfGameObjects)
        performUpdates(updates)
        expect:
        fixedSizeFrameCache.getLastFrames(framesRequested).size() == framesReceived
        where:
        nrOfGameObjects | updates | framesRequested | framesReceived
        1               | 0       | 0               | 0
        11              | 5       | 2               | 2
        100             | 5       | 6               | 5
    }

    private createGameObjects(int n) {
        gameObjects = (0..n - 1).collect {
            def gameObject = new GameObjectBuilder<>(BaseGameObject).setBody(new ShapeBody(new Point(pos: Vector.zeroVector()))).build()
            gameObject.setId(n)
            return gameObject
        }
        gameObjectProvider.getGameObjects() >> gameObjects
    }

    private frameCacheToBeUpdatedCorrectly() {
        def frameCacheContent = fixedSizeFrameCache.getLastFrames(1)
        Map<Long, Shape> expectedGameObjectShapeCache = gameObjects.collectEntries { [(it.id): it.body.shape] }
        def expectedFrameCacheContent = [new FrameState(gameObjectShapeCache: expectedGameObjectShapeCache)]
        frameCacheContent == expectedFrameCacheContent
    }

    private performUpdates(int n) {
        (0..n - 1).each {
            fixedSizeFrameCache.update()
        }

    }
}
