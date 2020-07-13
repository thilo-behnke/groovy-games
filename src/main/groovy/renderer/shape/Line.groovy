package renderer.shape

import global.geom.Vector
import renderer.destination.RenderDestination
import renderer.renderObjects.RenderNode

class Line implements Shape {
    Vector start
    Vector end

    @Override
    RenderNode render(RenderDestination renderDestination) {
        return renderDestination.drawLine(start, end)
    }
}
