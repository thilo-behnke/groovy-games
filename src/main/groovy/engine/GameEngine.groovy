package engine


import global.DateProvider
import groovy.util.logging.Log4j

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

@Log4j
class GameEngine {
    private DateProvider dateProvider
    private SceneProvider sceneProvider

    private GameScene activeScene

    private isRunning = false
    private long lastTimestamp

    private Thread thread

    private ExecutorService executorService

    GameEngine(DateProvider dateProvider, SceneProvider sceneProvider) {
        this.dateProvider = dateProvider
        this.sceneProvider = sceneProvider

        executorService = Executors.newFixedThreadPool(1)
    }

    void start() {
        isRunning = true
        // TODO: Does not seem like a good way. How can the GameEngine stay alive while the game loop thread is running?
        Runnable runnable = () -> {startGameLoop()}
        executorService.invokeAll([startGameLoop])
    }

    private startGameLoop() {
        while(isRunning) {
            long now = dateProvider.now()
            long delta = now - lastTimestamp
            log.debug("Updating Game. Time: ${now}. Delta: ${delta}".toString())
            activeScene.update(now, delta)
            lastTimestamp = now
        }
    }

    void stop() {
        sceneProvider.getAll().each {scene -> removeScene(scene.name)}
        isRunning = false
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
        if(activeScene && stopActiveScene) {
            activeScene.setState(GameSceneState.STOPPED)
        } else if(activeScene) {
            activeScene.setState(GameSceneState.PAUSED)
        }
        activeScene = scene
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
