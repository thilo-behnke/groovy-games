package org.tb.gg.engine

import org.tb.gg.GameEngineProvider
import org.tb.gg.di.Inject

trait Game {
    @Inject SceneManager sceneManager

    GameEngine gameEngine = GameEngineProvider.provideGameEngine()

    abstract void runGame()

    void stopGame() {
        GameEngineProvider.shutdownGameEngine()
    }

    void addScene(GameScene scene, activate) {
        sceneManager.addScene(scene, activate)
    }
}