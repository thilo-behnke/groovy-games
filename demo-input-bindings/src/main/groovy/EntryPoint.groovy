import org.tb.gg.di.ServiceProxyProvider
import org.tb.gg.di.creator.DefaultConstructorServiceCreator
import org.tb.gg.di.definition.Singleton
import org.tb.gg.di.scanner.ClasspathServiceScanner
import org.tb.gg.env.EnvironmentService
import org.tb.gg.env.EnvironmentSettings
import org.tb.gg.env.Graphics
import org.tb.gg.input.actions.factory.AbstractInputActionProviderFactory

//GameEngine gameEngine = GameEngineProvider.provideGameEngine()
//
//def gameObjectProvider = new GameObjectProvider()
//
//def defaultScene = new DefaultGameScene('default', gameObjectProvider)
//gameEngine.addScene(defaultScene)
//gameEngine.changeScene(defaultScene.name)
//
//gameEngine.start()


def res = new ClasspathServiceScanner().scanForServices(Singleton.class)
def services = new DefaultConstructorServiceCreator().createServices(res)
System.println(services)
def envService = (EnvironmentService) services.first()
envService.setEnvironment(new EnvironmentSettings(graphics: Graphics.SWING))
System.println(envService.getEnvironment())
ServiceProxyProvider.setService(envService)

def newFac = new AbstractInputActionProviderFactory()
System.println(newFac.checkEnv())