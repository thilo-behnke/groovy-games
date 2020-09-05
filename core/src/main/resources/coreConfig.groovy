import org.tb.gg.collision.DefaultCollisionCoordinator
import org.tb.gg.collision.DefaultCollisionDetector
import org.tb.gg.collision.handler.DirectionCollisionTypeHandler
import org.tb.gg.collision.strategy.DistanceHeuristicCollisionCheckSelectionStrategy
import org.tb.gg.engine.framecache.FixedSizeFrameCache
import org.tb.gg.env.Graphics
import org.tb.gg.events.DefaultEventManager
import org.tb.gg.global.DefaultDateProvider
import org.tb.gg.input.awt.SwingMouseEventAdapter
import org.tb.gg.renderer.destination.JPanelDestination
import org.tb.gg.resources.SwingResourceLoader

services = {
    def graphicsAPI = System.getProperty('graphicsAPI');
    if (!graphicsAPI || graphicsAPI == Graphics.SWING.name()) {
        mouseEventProvider(SwingMouseEventAdapter)
        resourceLoader(SwingResourceLoader)
        renderDestination(JPanelDestination)
    } else {
        throw new IllegalArgumentException("No graphis api implementation available for ${graphicsAPI}".toString())
    }

    dateProvider(DefaultDateProvider)
    frameCache(FixedSizeFrameCache)

    collisionCoordinator(DefaultCollisionCoordinator)
    collisionDetector(DefaultCollisionDetector)
    collisionCheckSelectionStrategy(DistanceHeuristicCollisionCheckSelectionStrategy)
    collisionTypeHandler(DirectionCollisionTypeHandler)

    eventManager(DefaultEventManager)
}
