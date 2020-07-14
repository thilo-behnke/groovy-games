package renderer.shape

import global.geom.FVector
import global.geom.Vector
import renderer.destination.RenderDestination
import renderer.renderObjects.RenderNode

class Line implements Shape {
    Vector start
    Vector end

    Line(FVector start, FVector end) {
        this.start = start
        this.end = end
    }

    @Override
    RenderNode render(RenderDestination renderDestination) {
        return renderDestination.drawLine(start, end)
    }
}