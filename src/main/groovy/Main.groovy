import engine.DefaultGameScene
import engine.GameEngine
import gameObject.DefaultGameObjectProvider
import global.DefaultDateProvider

class Main {
    static void main(String[] args) {
        def gameObjectProvider = new DefaultGameObjectProvider()
        def dateProvider = new DefaultDateProvider()

        def gameEngine = new GameEngine(dateProvider)

        def defaultScene = new DefaultGameScene('default', gameObjectProvider)
        gameEngine.addScene(defaultScene)
        gameEngine.changeScene(defaultScene.name)

        gameEngine.start()
    }
}