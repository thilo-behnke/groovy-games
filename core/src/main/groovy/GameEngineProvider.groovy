import org.tb.gg.di.DependencyInjectionHandler
import org.tb.gg.di.Inject
import org.tb.gg.engine.DefaultSceneProvider
import org.tb.gg.engine.GameEngine
import org.tb.gg.engine.GameEngineExecutionRuleEngine
import org.tb.gg.env.EnvironmentAnalyzer
import org.tb.gg.env.EnvironmentService
import org.tb.gg.env.SystemPropertiesEnvironmentAnalyzer
import org.tb.gg.global.DefaultDateProvider
import org.tb.gg.input.actions.KeyPressInputActionProvider
import org.tb.gg.input.actions.InputActionRegistry
import org.tb.gg.input.awt.SwingKeyEventAdapter
import org.tb.gg.renderer.DefaultRenderer
import org.tb.gg.utils.HaltingExecutorService

class GameEngineProvider {

    private DependencyInjectionHandler dependencyInjectionHandler = new DependencyInjectionHandler()
    private EnvironmentAnalyzer environmentAnalyzer = new SystemPropertiesEnvironmentAnalyzer()

    @Inject
    private EnvironmentService environmentService

    // TODO: It would be great to have something here like in Spring, e.g. auto detect files with ending Scene and startup the game that way.
    GameEngine provideGameEngine() {
        configureDependencyInjection()
        configureEnvironment()
        bootstrap()
    }

    private void configureDependencyInjection() {
        dependencyInjectionHandler.injectDependencies()
    }

    private void configureEnvironment() {
        def envService = (EnvironmentService) dependencyInjectionHandler.getService(EnvironmentService.getName())
        def graphics = environmentAnalyzer.getGraphics()
        envService.setEnvironment(graphics)
    }

    private GameEngine bootstrap() {
        def dateProvider = new DefaultDateProvider()
        def sceneProvider = new DefaultSceneProvider()
        def executorService = new HaltingExecutorService()
        def executionRuleEngine = new GameEngineExecutionRuleEngine()
//        executionRuleEngine << ShutdownAfterFixedNumberOfCyclesExecutionRule.nrOfCycles(10000)

        // TODO: This should happen somewhere else, as multiple instances could exist...
        def inputActionRegistry = new InputActionRegistry()
        def keyEventSubject = new SwingKeyEventAdapter()
        def inputActionProvider = new KeyPressInputActionProvider(inputActionRegistry, keyEventSubject)

        def renderer = new DefaultRenderer(renderDestination: environmentService.environment.renderDestination)

        def gameEngine = new GameEngine(executorService, dateProvider, sceneProvider, renderer)
        gameEngine.setExecutionRuleEngine(executionRuleEngine)

        return gameEngine
    }
}