package gameObject

interface GameObjectProvider {
	Set<GameObject> get();
}

class DefaultGameObjectProvider implements GameObjectProvider {
	@Override
	Set<GameObject> get() {
		return Collections.emptySet()
	}
}