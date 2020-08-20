package org.tb.gg.gameObject.components.render


import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode

abstract class RenderComponent implements Lifecycle {
    BaseGameObject parent

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
