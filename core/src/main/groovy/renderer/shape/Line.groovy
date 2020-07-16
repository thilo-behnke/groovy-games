package renderer.shape


import global.geom.Vector
import renderer.destination.RenderDestination
import renderer.options.RenderOptions
import renderer.renderObjects.RenderNode

class Line implements Shape {
    Vector start
    Vector end

    Line(Vector start, Vector end) {
        this.start = start
        this.end = end
    }

    @Override
    void render(RenderDestination renderDestination, RenderOptions options) {
        renderDestination.drawLine(start, end, options)
    }
}
