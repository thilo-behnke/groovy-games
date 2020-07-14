package gameObject.player

import gameObject.components.RenderComponent
import global.geom.Vector
import renderer.renderObjects.RenderNode
import renderer.shape.Line

class PlayerRenderComponent extends RenderComponent {
    @Override
    RenderNode getRenderNode() {
        return RenderNode.leaf(new Line(new Vector(x: parent.position.x, y: parent.position.y), new Vector(x: parent.position.x + 100, y: parent.position.y + 100)))
    }
}
