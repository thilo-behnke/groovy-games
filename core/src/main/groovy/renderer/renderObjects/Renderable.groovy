package renderer.renderObjects

import renderer.destination.RenderDestination
import renderer.options.RenderOptions

interface Renderable {
    void render(RenderDestination renderDestination, RenderOptions options)
}