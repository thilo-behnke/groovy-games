package gameObject.components

import gameObject.GameObject
import renderer.destination.RenderDestination
import renderer.renderObjects.RenderNode
import renderer.renderObjects.Renderable

abstract class RenderComponent implements Renderable {
    GameObject parent

    abstract RenderNode getRenderNode()

    @Override
    RenderNode render(RenderDestination renderDestination) {
        // TODO: Recursive call?
        return getRenderNode().renderObject.render(renderDestination)
    }
}
