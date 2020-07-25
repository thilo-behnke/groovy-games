package org.tb.gg.gameObject

import org.tb.gg.gameObject.components.RenderComponent
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.renderer.shape.Line

// TODO: Add Keyboard visualization to show pressed keys.
class KeyboardRenderComponent extends RenderComponent {
    @Override
    RenderNode getRenderNode() {
        return RenderNode.leaf(new Line(Vector.unitVector(), Vector.zeroVector()))
    }
}
