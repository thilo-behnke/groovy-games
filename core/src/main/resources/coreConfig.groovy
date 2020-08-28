import org.tb.gg.collision.DefaultCollisionCoordinator
import org.tb.gg.collision.DefaultCollisionDetector
import org.tb.gg.collision.handler.DirectionCollisionTypeHandler
import org.tb.gg.engine.framecache.FixedSizeFrameCache
import org.tb.gg.events.DefaultEventManager
import org.tb.gg.global.DefaultDateProvider
import org.tb.gg.input.awt.SwingMouseEventAdapter

services = {
    dateProvider(DefaultDateProvider)
    frameCache(FixedSizeFrameCache)
    mouseEventProvider(SwingMouseEventAdapter)
    collisionCoordinator(DefaultCollisionCoordinator)
    collisionDetector(DefaultCollisionDetector)
    collisionTypeHandler(DirectionCollisionTypeHandler)
    eventManager(DefaultEventManager)
}
