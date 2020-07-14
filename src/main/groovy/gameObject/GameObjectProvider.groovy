package gameObject

class GameObjectProvider {
    private Set<GameObject> gameObjects = []

	Set<GameObject> leftShift(GameObject gameObject) {
		return gameObjects << gameObject
	}

	Set<GameObject> getGameObjects() {
        return gameObjects
	}
}
