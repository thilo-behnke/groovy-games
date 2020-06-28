import engine.DefaultGameScene
import engine.DefaultSceneProvider
import engine.FixedCycleGameEngineExecutionCondition
import engine.GameEngine
import gameObject.DefaultGameObjectProvider
import global.DefaultDateProvider

import java.util.concurrent.Executors

class Main {
    static void main(String[] args) {
        def gameObjectProvider = new DefaultGameObjectProvider()
        def dateProvider = new DefaultDateProvider()
        def sceneProvider = new DefaultSceneProvider()
        def executorService = Executors.newFixedThreadPool(1)

        def gameEngine = new GameEngine(executorService, dateProvider, sceneProvider)
        gameEngine.setExecutionCondition(FixedCycleGameEngineExecutionCondition.nrOfCycles(100))

        def defaultScene = new DefaultGameScene('default', gameObjectProvider)
        gameEngine.addScene(defaultScene)
        gameEngine.changeScene(defaultScene.name)

        gameEngine.start()
    }
}