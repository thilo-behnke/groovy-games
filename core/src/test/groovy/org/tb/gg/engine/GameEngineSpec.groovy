package org.tb.gg.engine

import org.tb.gg.di.ServiceProvider
import org.tb.gg.gameObject.GameObjectProvider
import org.tb.gg.global.DateProvider
import org.tb.gg.renderer.Renderer
import spock.lang.Specification
import spock.lang.Unroll
import org.tb.gg.utils.HaltingExecutorService

import java.util.concurrent.ExecutorService

@Unroll
class GameEngineSpec extends Specification {

    GameEngine gameEngine
    ExecutorService executorService
    Renderer renderer

    Set<GameScene> scenes = []
    GameScene activeScene

    int receivedUpdates = 0

    def setup() {
        receivedUpdates = 0
        def dateProviderMock = Mock(DateProvider)
        ServiceProvider.setService(new GameObjectProvider())
        executorService = new HaltingExecutorService()
        renderer = Mock(Renderer)
        gameEngine = new GameEngine(executorService, dateProviderMock, renderer)
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
        expectScenesToBeUpdatedAndRendered()
    }

    def 'running the game loop without an active scene'() {
        when:
        configureGameEngineWithScene()
        runGameEngine()
        then:
        expectScenesToBeUpdatedAndRendered()
    }

    def 'running the game loop with an active scene'() {
        when:
        def sceneName = configureGameEngineWithScene(true)
        runGameEngine()
        then:
        expectScenesToBeUpdatedAndRendered(sceneName)
    }

    private runGameEngine() {
        gameEngine.start()
    }

    private configureGameEngineWithScene(activate = false) {
        def scene = new GameScene('sceneOne')
        gameEngine.addScene(scene)
        if(activate) {
            gameEngine.changeScene(scene.name)
            activeScene = scene
        }
        return scene.name
    }

    private expectScenesToBeUpdatedAndRendered(String ...sceneNames) {
        def scenes = scenes.findAll {scene -> sceneNames.find {name -> scene.name == name}}
        for(scene in scenes) {
            1 * scene.update()
            1 * renderer.render(scenes)
        }
        return true
    }

}
