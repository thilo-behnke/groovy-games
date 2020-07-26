package org.tb.gg.gameObject

import java.util.concurrent.atomic.AtomicLong

class GameObjectProvider {
	private AtomicLong idCounter = new AtomicLong()

	private Set<GameObject> gameObjects = []

	Set<GameObject> leftShift(GameObject gameObject) {
		def id = idCounter.incrementAndGet()
		gameObject.setId(id)

		return gameObjects << gameObject
	}

	Set<GameObject> getGameObjects() {
        return gameObjects
	}
}
