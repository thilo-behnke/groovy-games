package gameObject.components

import gameObject.GameObject
import renderer.destination.RenderDestination
import renderer.options.RenderOptions
import renderer.renderObjects.RenderNode

abstract class RenderComponent {
    GameObject parent

    abstract RenderNode getRenderNode()

    protected void performRender(RenderNode renderNode, RenderDestination renderDestination) {
        if (renderNode.renderObject.isPresent()) {
            def renderOptions = renderNode.renderOptions.isPresent() ? renderNode.renderOptions.get() : RenderOptions.empty
            def renderObj = renderNode.renderObject.get()
            renderObj.render(renderDestination, renderOptions)
        }
        // Note: The order is inverted here, because being drawn last usually means being rendered on top.
        def orderedChildren = renderNode.childNodes.sort{a, b -> b.order <=> a.order}
        orderedChildren.forEach({ performRender(it, renderDestination) })
    }

    void render(RenderDestination renderDestination) {
        performRender(getRenderNode(), renderDestination)
    }
}
