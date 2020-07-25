import org.tb.gg.di.creator.DefaultConstructorServiceCreator
import org.tb.gg.di.scanner.SingletonServiceScanner
import org.tb.gg.env.EnvironmentService
import org.tb.gg.env.EnvironmentSettings
import org.tb.gg.env.Graphics

//GameEngine gameEngine = GameEngineProvider.provideGameEngine()
//
//def gameObjectProvider = new GameObjectProvider()
//
//def defaultScene = new DefaultGameScene('default', gameObjectProvider)
//gameEngine.addScene(defaultScene)
//gameEngine.changeScene(defaultScene.name)
//
//gameEngine.start()


def res = new SingletonServiceScanner().scanForServices()
def services = new DefaultConstructorServiceCreator().createServices(res)
System.println(services)
def envService = (EnvironmentService) services.first()
envService.setEnvironment(new EnvironmentSettings(graphics: Graphics.SWING))
System.println(envService.getEnvironment())
