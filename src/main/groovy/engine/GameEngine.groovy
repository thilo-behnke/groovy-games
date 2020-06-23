package engine

import engine.exception.DuplicateGameSceneException
import global.DateProvider
import groovy.util.logging.Log4j

@Log4j
class GameEngine {
    private DateProvider dateProvider

    private GameScene activeScene
    private Map<String, GameScene> scenes = [:]

    private isRunning = false
    private long lastTimestamp

    GameEngine(DateProvider dateProvider) {
        this.dateProvider = dateProvider
    }

    void start() {
        isRunning = true
        startGameLoop()
    }

    private startGameLoop() {
        while(isRunning) {
            long now = dateProvider.now()
            long delta = now - lastTimestamp
            log.debug("Updating Game. Time: ${now}. Delta: ${delta}".toString())
            scenes.values().each {scene -> scene.update(now, delta)}
            lastTimestamp = now
        }
    }

    void addScene(GameScene scene) {
        if(scenes.containsKey(scene.name)) {
            throw new DuplicateGameSceneException("There already exists a scene with the name ${name}.".toString())
        }
        scenes.put(scene.name, scene)
    }

    void changeScene(String name, boolean stopActiveScene = true) {
        GameScene scene = scenes.get(name)
        if(!scene) {
            log.debug("Tried to start a scene with name ${name}, but no such scene exists.")
            return
        }
        if(!activeScene || activeScene.name == name) {
            return
        }
        if(stopActiveScene) {
            scene.setState(GameSceneState.STOPPED)
        } else {
            scene.setState(GameSceneState.PAUSED)
        }
        activeScene = scene
        scene.setState(GameSceneState.RUNNING)
    }

    void removeScene(String name) {
        GameScene scene = scenes.get(name)
        if(!scene) {
            log.debug("Tried to stop a scene with name ${name}, but no such scene exists.")
            return
        }
        scene.setState(GameSceneState.STOPPED)
        scenes.remove(name)
    }
}
