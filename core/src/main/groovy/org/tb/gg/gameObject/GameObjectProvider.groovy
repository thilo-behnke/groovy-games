package org.tb.gg.gameObject

import org.tb.gg.di.definition.Singleton

import java.util.concurrent.atomic.AtomicLong

class GameObjectProvider implements Singleton {
	private AtomicLong idCounter = new AtomicLong()

	private Set<GameObject> gameObjects = []

	Set<GameObject> leftShift(GameObject gameObject) {
        addGameObject(gameObject)
	}

	Set<GameObject> addGameObject(GameObject gameObject) {
		def id = idCounter.incrementAndGet()
		gameObject.setId(id)
		gameObject.init()

		return gameObjects << gameObject
	}

	int removeGameObjects(GameObject ...toRemove) {
		toRemove.each {
			it.destroy()
			gameObjects.remove(it)
		}
		return toRemove.size()
	}


	Set<GameObject> getGameObjects() {
        return gameObjects
	}

	@Override
	void init() {

	}

	@Override
	void destroy() {

	}
}
