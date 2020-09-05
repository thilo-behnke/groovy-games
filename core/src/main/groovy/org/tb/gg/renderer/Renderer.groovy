package org.tb.gg.renderer

import org.tb.gg.di.Inject
import org.tb.gg.di.definition.Singleton
import org.tb.gg.engine.GameScene
import org.tb.gg.renderer.destination.RenderDestination

interface Renderer extends Singleton {
    void render(Set<GameScene> scenes)
}

class DefaultRenderer implements Renderer {
    @Inject
    private RenderDestination renderDestination

    void render(Set<GameScene> scenes) {
        scenes.forEach({ scene ->
            def gameObjects = scene.getGameObjects()
            gameObjects.forEach({obj ->
                obj.render(renderDestination)
            })
            renderDestination.refresh()
        })
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}