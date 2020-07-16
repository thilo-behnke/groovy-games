package gameObject.player

import gameObject.components.RenderComponent
import renderer.options.DrawColor
import renderer.options.RenderOptions
import renderer.renderObjects.RenderNode
import renderer.shape.Circle
import renderer.shape.Point

class ClockRenderComponent extends RenderComponent {
    @Override
    RenderNode getRenderNode() {
        Clock parent = (Clock) parent
        return RenderNode.node(
                new Point(pos: parent.center),
                new RenderOptions(drawColor: DrawColor.BLACK),
                [
                        RenderNode.leaf(new Circle(center: parent.center, radius: parent.orientation.length()), new RenderOptions(drawColor: DrawColor.RED)),
                        RenderNode.leaf(new Point(pos: parent.position), new RenderOptions(drawColor: DrawColor.BLACK))
                ]
        )
    }
}
