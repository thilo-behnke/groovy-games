package org.tb.gg.gameObject.components.render

import org.tb.gg.di.Inject
import org.tb.gg.env.EnvironmentService
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.renderer.renderObjects.Renderable

class DefaultRenderComponent extends RenderComponent {
    @Inject
    EnvironmentService environmentService

    @Override
    RenderNode getRenderNode() {
        def debugNodes = environmentService.environment.debugMode ? getDebugNodes() : []
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
