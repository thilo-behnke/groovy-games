package org.tb.gg.engine

import org.tb.gg.GameEngineProvider

trait Game {
    GameEngine gameEngine = GameEngineProvider.provideGameEngine()

    abstract void runGame()

    void stopGame() {
        GameEngineProvider.shutdownGameEngine()
    }
}