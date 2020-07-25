package org.tb.gg.engine

import org.tb.gg.engine.exception.DuplicateGameSceneException

interface SceneProvider {
    void add(GameScene scene)
    void remove(String sceneName)
    Optional<GameScene> get(String sceneName)
    Set<GameScene> getAll()
}

class DefaultSceneProvider implements SceneProvider {
    private Map<String, GameScene> scenes = [:]

    @Override
    void add(GameScene scene) {
        if(scenes.containsKey(scene.name)) {
            throw new DuplicateGameSceneException("There already exists a scene with the name ${scene.name}.".toString())
        }
        scenes.put(scene.name, scene)
    }

    @Override
    void remove(String sceneName) {
        scenes.remove(sceneName)
    }

    @Override
    Optional<GameScene> get(String sceneName) {
        return Optional.of(scenes.get(sceneName))
    }

    @Override
    Set<GameScene> getAll() {
        return scenes.values()
    }
}
