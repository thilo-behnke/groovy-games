package org.tb.gg.gameObject

import java.util.concurrent.atomic.AtomicLong

class GameObjectProvider {
	private AtomicLong idCounter = new AtomicLong()

	private Set<GameObject> gameObjects = []

	Set<GameObject> leftShift(GameObject gameObject) {
        addGameObject(gameObject)
	}

	Set<GameObject> addGameObject(GameObject gameObject) {
		def id = idCounter.incrementAndGet()
		gameObject.setId(id)
		gameObject.onInit()

		return gameObjects << gameObject
	}

	boolean removeGameObject(GameObject gameObject) {
		gameObject.onDestroy()
		gameObjects.remove(gameObject)
	}


	Set<GameObject> getGameObjects() {
        return gameObjects
	}
}
