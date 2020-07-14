package engine


import gameObject.GameObjectProvider
import global.DateProvider
import renderer.Renderer
import spock.lang.Specification
import spock.lang.Unroll
import utils.HaltingExecutorService

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Unroll
class GameEngineSpec extends Specification {

    GameEngine gameEngine
    ExecutorService executorService

    Set<GameScene> scenes = []
    GameScene activeScene

    int receivedUpdates = 0

    def setup() {
        receivedUpdates = 0
        def dateProviderMock = Mock(DateProvider)
        def sceneProvider = new DefaultSceneProvider()
        executorService = new HaltingExecutorService()
        def renderer = Mock(Renderer)
        gameEngine = new GameEngine(executorService, dateProviderMock, sceneProvider, renderer)
        pauseAfterEveryCycle(1)
    }

    private boolean pauseAfterEveryCycle(nrOfCycles = 1) {
        def executionRuleEngine = new GameEngineExecutionRuleEngine()
//        executionRuleEngine << ShutdownAfterFixedNumberOfCyclesExecutionRule.nrOfCycles(1)
        executionRuleEngine << new HaltEveryCycleExecutionRule()
        gameEngine.setExecutionRuleEngine(executionRuleEngine)
    }

    def cleanup() {
        gameEngine.stop()
    }

    def 'running the game loop without scenes'() {
        when:
        gameEngine.start()
        runGameEngine()
        then:
        expectScenesToBeUpdated()
    }

    def 'running the game loop without an active scene'() {
        when:
        configureGameEngineWithScene()
        runGameEngine()
        then:
        expectScenesToBeUpdated()
    }

    def 'running the game loop with an active scene'() {
        when:
        def sceneName = configureGameEngineWithScene(true)
        runGameEngine()
        then:
        expectScenesToBeUpdated(sceneName)
    }

    private runGameEngine() {
        gameEngine.start()
    }

    private configureGameEngineWithScene(activate = false) {
        def scene = new DefaultGameScene('sceneOne', new GameObjectProvider())
        gameEngine.addScene(scene)
        if(activate) {
            gameEngine.changeScene(scene.name)
            activeScene = scene
        }
        return scene.name
    }

    private expectScenesToBeUpdated(String ...sceneNames) {
        def scenes = scenes.findAll {scene -> sceneNames.find {name -> scene.name == name}}
        for(scene in scenes) {
            1 * scene.update()
        }
        return true
    }

}
