package gameObject

import gameObject.components.RenderComponent
import global.geom.Vector
import renderer.renderObjects.RenderNode
import renderer.shape.Line

// TODO: Add Keyboard visualization to show pressed keys.
class KeyboardRenderComponent extends RenderComponent {
    @Override
    RenderNode getRenderNode() {
        return RenderNode.leaf(new Line(Vector.unitVector(), Vector.zeroVector()))
    }
}
