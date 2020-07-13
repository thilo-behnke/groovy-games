package renderer

import engine.GameScene
import renderer.destination.RenderDestination

class Renderer {
    private RenderDestination renderDestination

    void render(Set<GameScene> scenes) {
        scenes.forEach({ scene ->
            def gameObjects = scene.getGameObjects()
            gameObjects.forEach({obj ->
                obj.render(renderDestination)
            })
        })
    }
}