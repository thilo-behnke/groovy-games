package gameObject.components

import gameObject.GameObject
import renderer.destination.RenderDestination
import renderer.renderObjects.RenderNode

abstract class RenderComponent {
    GameObject parent

    abstract RenderNode getRenderNode()

    void performRender(RenderNode renderNode, RenderDestination renderDestination) {
        renderNode.renderObject.render(renderDestination, renderNode.renderOptions)
        renderNode.childNodes.forEach({performRender(it, renderDestination)})
    }

    void render(RenderDestination renderDestination) {
        performRender(getRenderNode(), renderDestination)
    }
}
