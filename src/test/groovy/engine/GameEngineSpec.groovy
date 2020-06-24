package engine

import gameObject.DefaultGameObjectProvider
import gameObject.GameObjectProvider
import global.DateProvider
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class GameEngineSpec extends Specification {

    def GameEngine gameEngine

    def Set<GameScene> scenes = []
    def GameScene activeScene

    def setup() {
        def dateProviderMock = Mock(DateProvider)
        def sceneProvider = new DefaultSceneProvider()
        gameEngine = new GameEngine(dateProviderMock, sceneProvider)
    }

    def cleanup() {
        gameEngine.stop()
    }

    def 'running the game loop without scenes'() {
        when:
        gameEngine.start()
        then:
        gameEngine.isRunning()
    }

    def 'running the game loop without an active scene'() {
        when:
        configureGameEngineWithScene()
        gameEngine.start()
        then:
        gameEngine.isRunning()
        expectScenesToBeUpdated()
    }

    def 'running the game loop with an active scene'() {
        when:
        gameEngine.start()
        def sceneName = configureGameEngineWithScene(true)
        then:
        gameEngine.isRunning()
        // TODO: Does not work because loop is running in thread?
        expectScenesToBeUpdated(sceneName)
    }

    private configureGameEngineWithScene(activate = false) {
        def scene = new DefaultGameScene('sceneOne', Mock(GameObjectProvider))
        scenes.add(scene)
        gameEngine.addScene(scene)
        if(activate) {
            gameEngine.changeScene(scene.name)
            activeScene = scene
        }
        return scene.name
    }

    private expectScenesToBeUpdated(String ...sceneNames) {
        def scenes = scenes.findAll {scene -> sceneNames.find {name -> scene.name == name}}
        for(scene in scenes) {
            1 * scene.update
        }
        return true
    }

}
