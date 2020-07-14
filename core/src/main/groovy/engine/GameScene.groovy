package engine

import engine.helper.Updateable
import gameObject.GameObject
import gameObject.GameObjectProvider
import global.DateProvider
import groovy.util.logging.Log4j

interface GameScene extends Updateable {
    String name = 'default'

    void setState(GameSceneState sceneState)
    Set<GameObject> getGameObjects()
}

@Log4j
class DefaultGameScene implements GameScene {
    public String name
    private GameObjectProvider gameObjectProvider
    private DateProvider dateProvider

    private gameSceneState = GameSceneState.UNINITIALIZED

    DefaultGameScene(String name, GameObjectProvider gameObjectProvider) {
        this.name = name
        this.gameObjectProvider = gameObjectProvider
        this.dateProvider = dateProvider
    }

    @Override
    void update(Long timestamp, Long delta) {
        if(gameSceneState == GameSceneState.RUNNING) {
            gameObjectProvider.getGameObjects().each { obj -> obj.update(timestamp, delta)}
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
}
