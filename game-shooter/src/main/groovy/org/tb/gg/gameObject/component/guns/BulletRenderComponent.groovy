package org.tb.gg.gameObject.component.guns

import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.renderer.renderObjects.RenderNode

class BulletRenderComponent extends RenderComponent {
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
