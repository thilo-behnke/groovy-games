import org.tb.gg.collision.DefaultCollisionCoordinator
import org.tb.gg.collision.DefaultCollisionDetector
import org.tb.gg.collision.handler.DirectionCollisionTypeHandler
import org.tb.gg.collision.strategy.DistanceHeuristicCollisionCheckSelectionStrategy
import org.tb.gg.engine.framecache.FixedSizeFrameCache
import org.tb.gg.env.EnvironmentService
import org.tb.gg.env.Graphics
import org.tb.gg.events.DefaultEventManager
import org.tb.gg.global.DefaultDateProvider
import org.tb.gg.input.awt.SwingMouseEventAdapter
import org.tb.gg.renderer.DefaultRenderer
import org.tb.gg.resources.SwingResourceLoader

services = {
    def environmentService = new EnvironmentService()
    environmentService.init()

    def graphicsEnv = environmentService.constructGraphicsAPIEnvironment()
    renderDestination(graphicsEnv.renderDestination)
    graphicsAPIFrameProvider(graphicsEnv.frameProvider)

    def graphicsAPI = System.getProperty('graphicsAPI');
    if (!graphicsAPI || graphicsAPI == Graphics.SWING.name()) {
        mouseEventProvider(SwingMouseEventAdapter)
        resourceLoader(SwingResourceLoader)
    } else {
        throw new IllegalArgumentException("No graphis api implementation available for ${graphicsAPI}".toString())
    }

    renderer(DefaultRenderer)
    dateProvider(DefaultDateProvider)
    frameCache(FixedSizeFrameCache)

    collisionCoordinator(DefaultCollisionCoordinator)
    collisionDetector(DefaultCollisionDetector)
    collisionCheckSelectionStrategy(DistanceHeuristicCollisionCheckSelectionStrategy)
    collisionTypeHandler(DirectionCollisionTypeHandler)

    eventManager(DefaultEventManager)
}
