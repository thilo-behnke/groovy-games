package org.tb.gg.gameObject.components.render

import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.renderer.renderObjects.Renderable

class DefaultRenderComponent extends RenderComponent {
    @Override
    RenderNode getRenderNode() {
        return RenderNode.leaf(parent.body)
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
