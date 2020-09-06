package org.tb.gg

import org.tb.gg.di.DependencyInjectionHandler
import org.tb.gg.di.Inject
import org.tb.gg.di.definition.Service
import org.tb.gg.engine.GameEngine
import org.tb.gg.engine.GameEngineExecutionRuleEngine
import org.tb.gg.env.EnvironmentService
import org.tb.gg.global.DefaultDateProvider
import org.tb.gg.renderer.DefaultRenderer
import org.tb.gg.renderer.Renderer
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.utils.HaltingExecutorService

class GameEngineProvider {
    @Inject
    private static Renderer renderer

    private static GameEngine gameEngine
    private static List<Service> services

    // TODO: It would be great to have something here like in Spring, e.g. auto detect files with ending Scene and startup the game that way.
    static GameEngine provideGameEngine() {
        if (gameEngine) {
            return gameEngine
        }
        // TODO: First inject static services, then inject dynamic services (dependent on environment or other settings).
        configureDependencyInjection()
        initialize()
        bootstrapGameEngine()
    }

    static void shutdownGameEngine() {
        gameEngine.stop()
        services.each { it.destroy() }
    }

    private static void configureDependencyInjection() {
        services = new DependencyInjectionHandler().injectDependencies()
    }

    private static void initialize() {
        services.each {
            it.init()
        }
    }

    private static GameEngine bootstrapGameEngine() {
        def dateProvider = new DefaultDateProvider()
        def executorService = new HaltingExecutorService()
        def executionRuleEngine = new GameEngineExecutionRuleEngine()
//        executionRuleEngine << ShutdownAfterFixedNumberOfCyclesExecutionRule.nrOfCycles(10000)

        def gameEngine = new GameEngine(executorService, dateProvider, renderer)
        gameEngine.setExecutionRuleEngine(executionRuleEngine)

        this.gameEngine = gameEngine
        return gameEngine
    }
}