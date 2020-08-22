package org.tb.gg.engine

import org.tb.gg.collision.CollisionHandler
import org.tb.gg.collision.CollisionRegistry
import org.tb.gg.di.Inject
import org.tb.gg.di.MultiInject
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.global.DateProvider
import groovy.util.logging.Log4j
import org.tb.gg.spawner.Spawner

@Log4j
class GameScene {
    @Inject
    private GameObjectProvider gameObjectProvider
    @Inject
    private CollisionRegistry collisionRegistry
    @MultiInject
    private List<Spawner> spawners

    public String name
    private DateProvider dateProvider

    private gameSceneState = GameSceneState.UNINITIALIZED

    GameScene(String name) {
        this.name = name
        this.dateProvider = dateProvider
    }

    void update(Long timestamp, Long delta) {
        if (gameSceneState == GameSceneState.RUNNING) {
            // TODO: How to get rid of the casting here?
            def spawned = (Set<GameObject>) spawners.collectMany { Spawner<? extends GameObject> spawner -> (Set<GameObject>) spawner.spawn() }.collect { (GameObject) it }
            spawned.each {
                gameObjectProvider << it
            }
            def gameObjects = updateGameObjects()
            // TODO: Copy to avoid concurrent modification - but is this the right way?
            new HashSet<>(gameObjects).each { obj -> obj.update(timestamp, delta) }
            collisionRegistry.update(timestamp, delta)
        }
    }

    private Set<GameObject> updateGameObjects() {
        def gameObjects = gameObjectProvider.getGameObjects()
        def gameObjectsToDestroy = gameObjects.findAll { it.shouldPerish() }
        def nrDestroyed = gameObjectProvider.removeGameObjects(gameObjectsToDestroy as BaseGameObject[])
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
