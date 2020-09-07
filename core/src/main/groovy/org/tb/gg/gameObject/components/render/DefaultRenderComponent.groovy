package org.tb.gg.gameObject.components.render

import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.renderer.renderObjects.Renderable

class DefaultRenderComponent extends RenderComponent {
    @Override
    RenderNode getRenderNode() {
        def debugNodes = getDebugNodes()
        return RenderNode.node(
                [*debugNodes, RenderNode.leaf(parent.body)]
        )
    }

    protected List<RenderNode> getDebugNodes() {
        return []
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
