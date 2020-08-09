package org.tb.gg.gameObject.components.render

import org.tb.gg.renderer.renderObjects.RenderNode

class NoopRenderComponent extends RenderComponent {
    private static final comp = new NoopRenderComponent()

    private NoopRenderComponent() {
    }

    // Component does nothing, so just share it between components instead of creating it multiple times.
    static get() {
        return comp
    }

    @Override
    RenderNode getRenderNode() {
        return RenderNode.node([])
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
