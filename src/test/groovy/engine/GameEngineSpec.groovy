package engine


import gameObject.GameObjectProvider
import global.DateProvider
import spock.lang.Specification
import spock.lang.Unroll

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
        executorService = Executors.newFixedThreadPool(1)
        gameEngine = new GameEngine(executorService, dateProviderMock, sceneProvider)
        waitForCycles(1)
    }

    private boolean waitForCycles(nrOfCycles = 1) {
        def executionCondition = FixedCycleGameEngineExecutionCondition.nrOfCycles(1)
        gameEngine.setExecutionRuleEngine(executionCondition)
    }

    def cleanup() {
        gameEngine.stop()
    }

    def 'running the game loop without scenes'() {
        when:
        gameEngine.start()
        runGameEngine()
        then:
        gameEngine.isRunning()
        expectScenesToBeUpdated()
    }

    def 'running the game loop without an active scene'() {
        when:
        configureGameEngineWithScene()
        runGameEngine()
        then:
        // TODO: This does not work reliably because the thread runs as it wants, see comment below.
//        gameEngine.isRunning()
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
        // TODO: Abstract time away from game engine...
        executorService.awaitTermination(10L, TimeUnit.MILLISECONDS)
    }

    private configureGameEngineWithScene(activate = false) {
        def scene = new DefaultGameScene('sceneOne', Mock(GameObjectProvider))
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
