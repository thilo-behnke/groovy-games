package gameObject.components

import gameObject.GameObject
import renderer.destination.RenderDestination
import renderer.renderObjects.RenderNode

abstract class RenderComponent {
    GameObject parent

    abstract RenderNode getRenderNode()

    void performRender(RenderNode renderNode, RenderDestination renderDestination) {
        if (renderNode.renderObject.isPresent() && renderNode.renderOptions.isPresent()) {
            renderNode.renderObject.get().render(renderDestination, renderNode.renderOptions.get())
        }
        renderNode.childNodes.sort{a, b -> b.order <=> a.order}.forEach({ performRender(it, renderDestination) })
    }

    void render(RenderDestination renderDestination) {
        performRender(getRenderNode(), renderDestination)
    }
}
