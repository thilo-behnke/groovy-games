package renderer.shape

import global.geom.Vector
import renderer.destination.RenderDestination
import renderer.options.RenderOptions

class Circle implements Shape {
    Vector center
    Float radius

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        renderDestination.drawCircle(center, radius, options)
    }
}
