package engine

import global.DateProvider
import groovy.util.logging.Log4j

@Log4j
class GameEngine {
    private DateProvider dateProvider
    private SceneProvider sceneProvider

    private Optional<GameScene> activeScene = Optional.empty()

    private isRunning = false
    private long lastTimestamp

    private Thread thread

    GameEngine(DateProvider dateProvider, SceneProvider sceneProvider) {
        this.dateProvider = dateProvider
        this.sceneProvider = sceneProvider
    }

    void start() {
        isRunning = true
        // TODO: Does not seem like a good way.
        Runnable runnable = () -> {startGameLoop()}
        thread = new Thread(runnable)
        thread.start()
        thread.join()
    }

    private startGameLoop() {
        while(isRunning) {
            long now = dateProvider.now()
            long delta = now - lastTimestamp
            log.debug("Updating Game. Time: ${now}. Delta: ${delta}".toString())
            activeScene.ifPresent{scene -> scene.update(now, delta)}
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
