package org.tb.gg.engine

import groovy.util.logging.Log4j
import org.tb.gg.di.Inject
import org.tb.gg.di.definition.Singleton

@Log4j
class SceneManager implements Singleton {

    @Inject SceneProvider sceneProvider

    private Optional<GameScene> activeScene = Optional.empty()

    void changeActiveScene(String name) {
        Optional<GameScene> sceneOpt = sceneProvider.get(name)
        if (!sceneOpt.isPresent()) {
            log.debug("Tried to start a scene with name ${name}, but no such scene exists.")
            return
        }

        GameScene scene = sceneOpt.get()
        if (activeScene.isPresent() && activeScene.get().name == name) {
            return
        }
        activeScene.ifPresent { s -> s.setState(GameSceneState.STOPPED) }
        activeScene = Optional.of(scene)
        scene.setState(GameSceneState.RUNNING)
    }

    void addScene(GameScene scene, activate = false) {
        sceneProvider.add(scene)
        if (activate) {
            changeActiveScene(scene.name)
        }
    }

    void removeScene(String name) {
        Optional<GameScene> sceneOpt = sceneProvider.get(name)
        if (!sceneOpt.isPresent()) {
            log.debug("Tried to stop a scene with name ${name}, but no such scene exists.")
            return
        }
        GameScene scene = sceneOpt.get()
        scene.setState(GameSceneState.STOPPED)
        sceneProvider.remove(name)
    }

    Optional<GameScene> getActiveScene() {
        return activeScene
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}