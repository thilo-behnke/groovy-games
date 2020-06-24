import engine.DefaultGameScene
import engine.DefaultSceneProvider
import engine.GameEngine
import gameObject.DefaultGameObjectProvider
import global.DefaultDateProvider

class Main {
    static void main(String[] args) {
        def gameObjectProvider = new DefaultGameObjectProvider()
        def dateProvider = new DefaultDateProvider()
        def sceneProvider = new DefaultSceneProvider()

        def gameEngine = new GameEngine(dateProvider, sceneProvider)

        def defaultScene = new DefaultGameScene('default', gameObjectProvider)
        gameEngine.addScene(defaultScene)
        gameEngine.changeScene(defaultScene.name)

        gameEngine.start()
    }
}