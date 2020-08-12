package org.tb.gg.engine

import org.tb.gg.di.definition.Singleton
import org.tb.gg.engine.exception.DuplicateGameSceneException

class SceneProvider {
    private Map<String, GameScene> scenes = [:]

    void add(GameScene scene) {
        if(scenes.containsKey(scene.name)) {
            throw new DuplicateGameSceneException("There already exists a scene with the name ${scene.name}.".toString())
        }
        scenes.put(scene.name, scene)
    }

    void remove(String sceneName) {
        scenes.remove(sceneName)
    }

    Optional<GameScene> get(String sceneName) {
        return Optional.of(scenes.get(sceneName))
    }

    Set<GameScene> getAll() {
        return scenes.values()
    }

    void init() {

    }

    void destroy() {

    }
}
