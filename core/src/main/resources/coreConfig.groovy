import org.tb.gg.collision.DefaultCollisionCoordinator
import org.tb.gg.collision.DefaultCollisionDetector
import org.tb.gg.collision.handler.DirectionCollisionTypeHandler
import org.tb.gg.collision.strategy.DistanceHeuristicCollisionCheckSelectionStrategy
import org.tb.gg.engine.framecache.FixedSizeFrameCache
import org.tb.gg.env.DefaultEnvironmentService
import org.tb.gg.env.EnvironmentService
import org.tb.gg.env.SystemPropertiesEnvironmentAnalyzer
import org.tb.gg.env.systemProperty.DefaultSystemPropertyProvider
import org.tb.gg.events.DefaultEventManager
import org.tb.gg.global.DefaultDateProvider
import org.tb.gg.renderer.DefaultRenderer

services = {
    def environmentServiceInstance = new DefaultEnvironmentService(new SystemPropertiesEnvironmentAnalyzer(new DefaultSystemPropertyProvider()))
    environmentServiceInstance.init()

    environmentService(environmentServiceInstance)

    def graphicsEnv = environmentServiceInstance.constructGraphicsAPIEnvironment()
    renderDestination(graphicsEnv.renderDestination)
    graphicsAPIFrameProvider(graphicsEnv.frameProvider)
    mouseEventProvider(graphicsEnv.mouseEventProvider)
    resourceLoader(graphicsEnv.resourceLoader)

    renderer(DefaultRenderer)
    dateProvider(DefaultDateProvider)
    frameCache(FixedSizeFrameCache)

    collisionCoordinator(DefaultCollisionCoordinator)
    collisionDetector(DefaultCollisionDetector)
    collisionCheckSelectionStrategy(DistanceHeuristicCollisionCheckSelectionStrategy)
    collisionTypeHandler(DirectionCollisionTypeHandler)

    eventManager(DefaultEventManager)
}
