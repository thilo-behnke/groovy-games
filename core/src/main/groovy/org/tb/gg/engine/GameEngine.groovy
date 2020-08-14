package org.tb.gg.engine

import groovy.util.logging.Log4j
import org.tb.gg.di.Inject
import org.tb.gg.global.DateProvider
import org.tb.gg.renderer.Renderer
import org.tb.gg.utils.HaltingExecutorService
import org.tb.gg.world.WorldState
import org.tb.gg.world.WorldStateProvider

enum GameEngineState {
    UNINITIALIZED, RUNNING, STOPPED, PAUSED
}

@Log4j
class GameEngine {

    @Inject SceneManager sceneManager
    @Inject WorldStateProvider worldStateProvider

    private static final defaultExecutionRuleEngine = new GameEngineExecutionRuleEngine()
    private DateProvider dateProvider
    private HaltingExecutorService executorService
    private Renderer renderer
    private GameEngineExecutionRuleEngine executionRuleEngine

    private state = GameEngineState.UNINITIALIZED
    private long lastTimestamp

    // TODO: Migrate to injection.
    GameEngine(HaltingExecutorService executorService, DateProvider dateProvider, Renderer renderer) {
        this.dateProvider = dateProvider
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
        while (state == GameEngineState.RUNNING || state == GameEngineState.PAUSED) {
            if (stopIfExecutionConditionIsMet()) {
                break
            }
            if (state != GameEngineState.PAUSED && haltIfExecutionConditionIsMet()) {
                break
            }
            if (state == GameEngineState.RUNNING) {
                // TODO: This should not be one task, but multiple per cycle:
                // - Render (Scene dependent)
                // - GameObject updates (Scene dependent)
                // - Inputs
                // - etc.
                executorService.submit({ updateGame() })
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
        if (executionRuleEngine.shouldHalt()) {
            halt()
            return true
        }
    }

    private updateGame() {
        long now = dateProvider.now()
        long delta = now - (lastTimestamp ?: now)
        // TODO: Make refresh period configurable.
        if (lastTimestamp && delta < 1000 / 60) {
            return
        }

        def worldState = new WorldState(currentLoopTimestamp: now)
        worldStateProvider.set(worldState)

        updateScenes(now, delta)
        renderScenes()
        updateExecutionRuleEngine(now, delta)
        lastTimestamp = now
    }


    private updateScenes(long now, long delta) {
        log.debug("Updating active scenes. Time: ${now}. Delta: ${delta}".toString())
        sceneManager.activeScene.ifPresent { scene -> scene.update(now, delta) }
    }

    private renderScenes() {
        log.debug("Rendering updated scenes.")
        sceneManager.activeScene.ifPresent({ renderer.render(new HashSet<GameScene>([it])) })
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
        state = GameEngineState.STOPPED
        executorService.shutdown()
    }

    boolean isRunning() {
        return this.state == GameEngineState.RUNNING
    }
}
