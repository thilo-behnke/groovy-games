package org.tb.gg.gameObject

class GameObjectProvider {
	private Long idCounter = 1

	private Set<GameObject> gameObjects = []

	Set<GameObject> leftShift(GameObject gameObject) {
		gameObject.setId(idCounter)
		idCounter = (Long) (idCounter + 1)
        
		return gameObjects << gameObject
	}

	Set<GameObject> getGameObjects() {
        return gameObjects
	}
}
