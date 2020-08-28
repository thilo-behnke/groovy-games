package org.tb.gg.engine.framecache

import org.tb.gg.di.Inject
import org.tb.gg.di.definition.Singleton
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.gameObject.shape.Shape

trait FrameCache implements Singleton {
    @Inject
    GameObjectProvider gameObjectProvider

    abstract void add(FrameState frame)

    abstract List<FrameState> getLastFrames(int n)

    abstract void clear()

    void update() {
        def frame = createFrameState()
        add(frame)
    }

    private FrameState createFrameState() {
        def gameObjectShapeCache = gameObjectProvider.gameObjects
                .findAll { it.body }
                .<Long, Shape, GameObject> collectEntries { [(it.id): it.body.shape.copy()] }
        new FrameState(gameObjectShapeCache: gameObjectShapeCache)
    }
}
