import engine.*
import gameObject.DefaultGameObjectProvider
import global.DefaultDateProvider
import utils.HaltingExecutorService

class Main {
    static void main(String[] args) {
        def gameObjectProvider = new DefaultGameObjectProvider()
        def dateProvider = new DefaultDateProvider()
        def sceneProvider = new DefaultSceneProvider()
        def executorService = new HaltingExecutorService()
        def executionRuleEngine = new GameEngineExecutionRuleEngine()
        executionRuleEngine << FixedCycleGameEngineExecutionCondition.nrOfCycles(10)

        def gameEngine = new GameEngine(executorService, dateProvider, sceneProvider)
        gameEngine.setExecutionRuleEngine(executionRuleEngine)

        def defaultScene = new DefaultGameScene('default', gameObjectProvider)
        gameEngine.addScene(defaultScene)
        gameEngine.changeScene(defaultScene.name)

        gameEngine.start()
    }
}