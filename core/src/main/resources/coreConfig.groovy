import org.tb.gg.collision.DefaultCollisionRegistry
import org.tb.gg.collision.DefaultCollisionHandler
import org.tb.gg.global.DefaultDateProvider
import org.tb.gg.input.awt.SwingMouseEventAdapter

services = {
    dateProvider(DefaultDateProvider)
    mouseEventProvider(SwingMouseEventAdapter)
    collisionRegistry(DefaultCollisionRegistry)
    collisionHandler(DefaultCollisionHandler)
}
