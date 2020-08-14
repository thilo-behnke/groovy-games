package org.tb.gg.engine


import org.tb.gg.collision.CollisionRegistry
import org.tb.gg.di.Inject
import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.global.DateProvider
import groovy.util.logging.Log4j

@Log4j
class GameScene {
    @Inject
    private GameObjectProvider gameObjectProvider
    @Inject
    private CollisionRegistry collisionRegistry

    public String name
    private DateProvider dateProvider

    private gameSceneState = GameSceneState.UNINITIALIZED

    GameScene(String name) {
        this.name = name
        this.dateProvider = dateProvider
    }

    void update(Long timestamp, Long delta) {
        if (gameSceneState == GameSceneState.RUNNING) {
            def gameObjects = updateGameObjects()
            // TODO: Copy to avoid concurrent modification - but is this the right way?
            new HashSet<>(gameObjects).each { obj -> obj.update(timestamp, delta) }
            collisionRegistry.update(timestamp, delta)
        }
    }

    private Set<GameObject> updateGameObjects() {
        def gameObjects = gameObjectProvider.getGameObjects()
        def gameObjectsToDestroy = gameObjects.findAll { it.shouldPerish() }
        def nrDestroyed = gameObjectProvider.removeGameObjects(gameObjectsToDestroy as GameObject[])
        if (nrDestroyed) {
            log.info("Destroyed ${nrDestroyed} game objects on scene update.".toString())
        }
        return gameObjectProvider.getGameObjects()
    }

    void setState(GameSceneState state) {
        gameSceneState = state
    }

    Set<GameObject> getGameObjects() {
        return gameObjectProvider.getGameObjects()
    }

    // TODO: Find a better way to delegate - currently not possible because of inject ast transformation.
    GameObjectProvider accessGameObjectProvider() {
        return gameObjectProvider
    }
}
