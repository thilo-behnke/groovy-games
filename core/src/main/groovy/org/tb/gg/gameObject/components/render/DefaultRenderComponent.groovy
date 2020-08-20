package org.tb.gg.gameObject.components.render

import org.tb.gg.renderer.renderObjects.RenderNode

class DefaultRenderComponent extends RenderComponent {
    @Override
    RenderNode getRenderNode() {
        return RenderNode.leaf(parent.body.shape)
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
