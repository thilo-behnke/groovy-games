package gameObject.components

import gameObject.GameObject
import renderer.destination.RenderDestination
import renderer.renderObjects.RenderNode
import renderer.renderObjects.Renderable

class RenderComponent implements Renderable {
    GameObject parent
    RenderNode renderNode

    @Override
    RenderNode render(RenderDestination renderDestination) {
        // TODO: Recursive call?
        return renderNode.renderObject.render(renderDestination)
    }
}
