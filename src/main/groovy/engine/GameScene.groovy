package engine

import gameObject.GameObjectProvider
import global.DateProvider
import groovy.util.logging.Log4j

interface GameScene {
    String name = 'default'

    void update(Long timestamp, Long delta)

    void setState(GameSceneState sceneState)
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
            gameObjectProvider.get().each {obj -> obj.update(timestamp, delta)}
        }
    }

    @Override
    void setState(GameSceneState state) {
        gameSceneState = state
    }
}
