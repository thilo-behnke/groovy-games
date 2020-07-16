package gameObject.player

import gameObject.components.RenderComponent
import global.geom.Vector
import renderer.renderObjects.RenderNode
import renderer.shape.Line
import renderer.shape.Point

class ClockRenderComponent extends RenderComponent {
    @Override
    RenderNode getRenderNode() {
        Clock parent = (Clock) parent
        return RenderNode.leaf(new Point(pos: parent.position))
    }
}
