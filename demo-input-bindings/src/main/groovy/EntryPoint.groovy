import org.tb.gg.di.provider.TopLevelClassDefinitionProvider
import org.tb.gg.di.scanner.SingletonServiceScanner

//GameEngine gameEngine = GameEngineProvider.provideGameEngine()
//
//def gameObjectProvider = new GameObjectProvider()
//
//def defaultScene = new DefaultGameScene('default', gameObjectProvider)
//gameEngine.addScene(defaultScene)
//gameEngine.changeScene(defaultScene.name)
//
//gameEngine.start()

def classDefs = new TopLevelClassDefinitionProvider().getClassDefinitions()
def res = new SingletonServiceScanner().scanForServices(classDefs)
System.println(res)