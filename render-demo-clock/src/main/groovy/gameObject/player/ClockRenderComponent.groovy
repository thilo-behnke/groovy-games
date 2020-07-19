package gameObject.player

import gameObject.components.RenderComponent
import renderer.options.DrawColor
import renderer.options.RenderOptions
import renderer.renderObjects.RenderNode
import renderer.shape.Circle
import renderer.shape.Line
import renderer.shape.Point

class ClockRenderComponent extends RenderComponent {
    @Override
    RenderNode getRenderNode() {
        Clock parent = (Clock) parent
        return RenderNode.node(
                // Center.
                new Point(pos: parent.center),
                new RenderOptions(drawColor: DrawColor.BLACK),
                [
                        // Clock circle.
                        RenderNode.leaf(new Circle(center: parent.center, radius: parent.radius), new RenderOptions(drawColor: DrawColor.BLACK)),
                        // Handles.
                        RenderNode.leaf(new Line(parent.center, parent.secondHandlePos), new RenderOptions(drawColor: DrawColor.RED)),
                        RenderNode.leaf(new Line(parent.center, parent.minuteHandlePos), new RenderOptions(drawColor: DrawColor.BLUE)),
                        RenderNode.leaf(new Line(parent.center, parent.hourHandlePos), new RenderOptions(drawColor: DrawColor.GREEN))

                ]
        )
    }
}
