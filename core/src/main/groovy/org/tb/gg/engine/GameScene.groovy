package org.tb.gg.engine

import org.tb.gg.collision.CollisionDetector
import org.tb.gg.collision.CollisionRegistry
import org.tb.gg.di.Inject
import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.global.DateProvider
import groovy.util.logging.Log4j

interface GameScene extends Updateable {
    String name = 'default'

    void setState(GameSceneState sceneState)
    Set<GameObject> getGameObjects()
}

@Log4j
class DefaultGameScene implements GameScene {
    @Inject
    private GameObjectProvider gameObjectProvider
    @Inject
    private CollisionRegistry collisionRegistry

    public String name
    private DateProvider dateProvider

    private gameSceneState = GameSceneState.UNINITIALIZED

    DefaultGameScene(String name) {
        this.name = name
        this.dateProvider = dateProvider
    }

    @Override
    void update(Long timestamp, Long delta) {
        if(gameSceneState == GameSceneState.RUNNING) {
            // TODO: Not the right place - maybe a scene should only update its own game objects?
            gameObjectProvider.getGameObjects().each { obj -> obj.update(timestamp, delta)}
            collisionRegistry.update(timestamp, delta)
        }
    }

    @Override
    void setState(GameSceneState state) {
        gameSceneState = state
    }

    @Override
    Set<GameObject> getGameObjects() {
        return gameObjectProvider.getGameObjects()
    }

    // TODO: Find a better way to delegate - currently not possible because of inject ast transformation.
    GameObjectProvider accessGameObjectProvider() {
        return gameObjectProvider
    }
}
