package engine

import global.DateProvider

import groovy.util.logging.Log4j

import java.util.concurrent.ExecutorService

@Log4j
class GameEngine {
    private DateProvider dateProvider
    private SceneProvider sceneProvider
    private ExecutorService executorService
    private Optional<GameEngineExecutionCondition> executionCondition = Optional.empty()

    private Optional<GameScene> activeScene = Optional.empty()

    private isRunning = false
    private long lastTimestamp

    GameEngine(ExecutorService executorService, DateProvider dateProvider, SceneProvider sceneProvider) {
        this.dateProvider = dateProvider
        this.sceneProvider = sceneProvider
        this.executorService = executorService
    }

    void setExecutionCondition(GameEngineExecutionCondition executionCondition) {
        this.executionCondition = Optional.of(executionCondition)
    }

    ExecutorService start() {
        isRunning = true
        executorService.execute(() -> startGameLoop())
        return executorService
    }

    private startGameLoop() {
        while(isRunning) {
            if(stopIfExecutionConditionIsMet()) {
                break
            }
            long now = dateProvider.now()
            long delta = now - (lastTimestamp ?: now)
            updateScenes(now, delta)
            updateExecutionCondition(now, delta)
            lastTimestamp = now
        }
    }

    private stopIfExecutionConditionIsMet() {
        if (executionCondition.isPresent() && executionCondition.get().shouldStop()) {
            stop()
            return true
        }
    }

    private updateScenes(long now, long delta) {
        log.debug("Updating Game. Time: ${now}. Delta: ${delta}".toString())
        activeScene.ifPresent{scene -> scene.update(now, delta)}
    }

    private updateExecutionCondition(long now, long delta) {
        if(executionCondition.isPresent()) {
            executionCondition.get().onGameEngineCycle(now, delta)
        }
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
        if(activeScene && activeScene.name == name) {
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
