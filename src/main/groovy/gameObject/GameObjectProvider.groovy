package gameObject

interface GameObjectProvider {
	Set<GameObject> getGameObjects();
}

class DefaultGameObjectProvider implements GameObjectProvider {
	@Override
	Set<GameObject> getGameObjects() {
		return Collections.emptySet()
	}
}