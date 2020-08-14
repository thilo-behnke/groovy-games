package org.tb.gg.gameObject

import org.tb.gg.di.definition.Singleton

import java.util.concurrent.atomic.AtomicLong

class GameObjectProvider implements Singleton {
	private AtomicLong idCounter = new AtomicLong()

	private Set<BaseGameObject> gameObjects = []

	Set<BaseGameObject> leftShift(BaseGameObject gameObject) {
        addGameObject(gameObject)
	}

	Set<BaseGameObject> addGameObject(BaseGameObject gameObject) {
		def id = idCounter.incrementAndGet()
		gameObject.setId(id)
		gameObject.init()

		return gameObjects << gameObject
	}

	int removeGameObjects(BaseGameObject ...toRemove) {
		toRemove.each {
			it.destroy()
			gameObjects.remove(it)
		}
		return toRemove.size()
	}


	Set<BaseGameObject> getGameObjects() {
        return gameObjects
	}

	@Override
	void init() {

	}

	@Override
	void destroy() {

	}
}
