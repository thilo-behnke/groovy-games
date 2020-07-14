package renderer.renderObjects

import renderer.destination.RenderDestination

interface Renderable {
    RenderNode render(RenderDestination renderDestination)
}