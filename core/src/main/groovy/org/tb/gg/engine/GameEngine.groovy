package org.tb.gg.engine

import org.tb.gg.global.DateProvider

import groovy.util.logging.Log4j
import org.tb.gg.input.actions.InputActionProvider
import org.tb.gg.renderer.Renderer
import org.tb.gg.utils.HaltingExecutorService

enum GameEngineState {
    UNINITIALIZED, RUNNING, STOPPED, PAUSED
}

@Log4j
class GameEngine {
    private static final defaultExecutionRuleEngine = new GameEngineExecutionRuleEngine()
    private DateProvider dateProvider
    private SceneProvider sceneProvider
    private HaltingExecutorService executorService
    private Renderer renderer
    private InputActionProvider inputActionProvider
    private GameEngineExecutionRuleEngine executionRuleEngine

    // TODO: This should be an array of scenes.
    private Optional<GameScene> activeScene = Optional.empty()

    private state = GameEngineState.UNINITIALIZED
    private long lastTimestamp

    GameEngine(HaltingExecutorService executorService, DateProvider dateProvider, SceneProvider sceneProvider, Renderer renderer) {
        this.dateProvider = dateProvider
        this.sceneProvider = sceneProvider
        this.executorService = executorService
        this.renderer = renderer
    }

    void setExecutionRuleEngine(GameEngineExecutionRuleEngine gameEngineExecutionRuleEngine = defaultExecutionRuleEngine) {
        this.executionRuleEngine = gameEngineExecutionRuleEngine
    }

    void start() {
        state = GameEngineState.RUNNING
        startGameLoop()
    }

    private startGameLoop() {
        while(state == GameEngineState.RUNNING || state == GameEngineState.PAUSED) {
            if(stopIfExecutionConditionIsMet()) {
                break
            }
            if(state != GameEngineState.PAUSED && haltIfExecutionConditionIsMet()) {
                break
            }
            if(state == GameEngineState.RUNNING) {
                // TODO: This should not be one task, but multiple per cycle:
                // - Render (Scene dependent)
                // - GameObject updates (Scene dependent)
                // - Inputs
                // - etc.
                executorService.submit({updateGame()})
            }
        }
    }

    private stopIfExecutionConditionIsMet() {
        if (executionRuleEngine.shouldShutdown()) {
            stop()
            return true
        }
    }

    private haltIfExecutionConditionIsMet() {
        if(executionRuleEngine.shouldHalt()) {
            halt()
            return true
        }
    }

    private updateGame() {
        long now = dateProvider.now()
        long delta = now - (lastTimestamp ?: now)
        // TODO: Make refresh period configurable.
        if(lastTimestamp && delta < 1000 / 60) {
            return
        }
        updateScenes(now, delta)
        renderScenes()
        updateExecutionRuleEngine(now, delta)
        lastTimestamp = now
    }


    private updateScenes(long now, long delta) {
        log.info("Updating active scenes. Time: ${now}. Delta: ${delta}".toString())
        activeScene.ifPresent{scene -> scene.update(now, delta)}
    }

    private renderScenes() {
        log.info("Rendering updated scenes.")
        activeScene.ifPresent({renderer.render(new HashSet<GameScene>([it]))})
    }

    private updateExecutionRuleEngine(long now, long delta) {
        executionRuleEngine.onGameEngineCycle(now, delta)
    }

    void halt() {
        executorService.halt()
        state = GameEngineState.PAUSED
    }

    void resume() {
        executorService.continueExecution()
        state = GameEngineState.RUNNING
    }

    void stop() {
        sceneProvider.getAll().each {scene -> removeScene(scene.name)}
        state = GameEngineState.STOPPED
        executorService.shutdown()
    }

    boolean isRunning() {
        return this.state == GameEngineState.RUNNING
    }

    void addScene(GameScene scene) {
        sceneProvider.add(scene)
    }

    void changeScene(String name, boolean stopActiveScene = true) {
        Optional<GameScene> sceneOpt = sceneProvider.get(name)
        if(!sceneOpt.isPresent()) {
            log.debug("Tried to start a scene with name ${name}, but no such scene exists.")
            return
        }

        GameScene scene = sceneOpt.get()
        if(activeScene.isPresent() && activeScene.get().name == name) {
            return
        }
        if(stopActiveScene) {
            activeScene.ifPresent{s -> s.setState(GameSceneState.STOPPED)}
        } else {
            activeScene.ifPresent{s -> s.setState(GameSceneState.PAUSED)}
        }
        activeScene = Optional.of(scene)
        scene.setState(GameSceneState.RUNNING)
    }

    void removeScene(String name) {
        Optional<GameScene> sceneOpt = sceneProvider.get(name)
        if(!sceneOpt.isPresent()) {
            log.debug("Tried to stop a scene with name ${name}, but no such scene exists.")
            return
        }
        GameScene scene = sceneOpt.get()
        scene.setState(GameSceneState.STOPPED)
        sceneProvider.remove(name)
    }
}
