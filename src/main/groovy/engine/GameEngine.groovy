package engine

import global.DateProvider

import groovy.util.logging.Log4j

import java.util.concurrent.ExecutorService

@Log4j
class GameEngine {
    private static final defaultExecutionRuleEngine = new GameEngineExecutionRuleEngine()
    private DateProvider dateProvider
    private SceneProvider sceneProvider
    private ExecutorService executorService
    private GameEngineExecutionRuleEngine executionRuleEngine

    private Optional<GameScene> activeScene = Optional.empty()

    private isRunning = false
    private long lastTimestamp

    GameEngine(ExecutorService executorService, DateProvider dateProvider, SceneProvider sceneProvider) {
        this.dateProvider = dateProvider
        this.sceneProvider = sceneProvider
        this.executorService = executorService
    }

    void setExecutionRuleEngine(GameEngineExecutionRuleEngine gameEngineExecutionRuleEngine = defaultExecutionRuleEngine) {
        this.executionRuleEngine = gameEngineExecutionRuleEngine
    }

    void start() {
        isRunning = true
        startGameLoop()
    }

    private startGameLoop() {
        while(isRunning) {
            if(stopIfExecutionConditionIsMet()) {
                break
            }
            executorService.submit(() -> updateGame())
        }
    }

    private stopIfExecutionConditionIsMet() {
        if (executionRuleEngine.shouldShutdown()) {
            stop()
            return true
        }
    }

    private updateGame() {
        long now = dateProvider.now()
        long delta = now - (lastTimestamp ?: now)
        updateScenes(now, delta)
        updateExecutionRuleEngine(now, delta)
        lastTimestamp = now
    }


    private updateScenes(long now, long delta) {
        log.debug("Updating Game. Time: ${now}. Delta: ${delta}".toString())
        activeScene.ifPresent{scene -> scene.update(now, delta)}
    }

    private updateExecutionRuleEngine(long now, long delta) {
        executionRuleEngine.onGameEngineCycle(now, delta)
    }

    void stop() {
        sceneProvider.getAll().each {scene -> removeScene(scene.name)}
        isRunning = false
        executorService.shutdown()
    }

    boolean isRunning() {
        return this.isRunning
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
