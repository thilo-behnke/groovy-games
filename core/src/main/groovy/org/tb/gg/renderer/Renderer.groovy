package org.tb.gg.renderer

import org.tb.gg.engine.GameScene
import org.tb.gg.renderer.destination.RenderDestination

interface Renderer {
    void render(Set<GameScene> scenes)
}

class DefaultRenderer implements Renderer {
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
}