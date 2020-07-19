package gameObject.player

import gameObject.components.RenderComponent
import global.geom.CircleOperations
import global.geom.Vector
import renderer.options.DrawColor
import renderer.options.RenderOptions
import renderer.renderObjects.RenderNode
import renderer.shape.Circle
import renderer.shape.Line
import renderer.shape.Point

class ClockRenderComponent extends RenderComponent {

    private List<RenderNode> secondMarks
    private List<RenderNode> minuteMarks
    private List<RenderNode> hourMarks

    @Override
    RenderNode getRenderNode() {
        Clock parent = (Clock) parent
        // Clock marks.
        def hourMarks = getHourMarks()
        def nodes = [
                // Clock circle.
                RenderNode.leaf(new Circle(center: parent.center, radius: parent.radius), new RenderOptions(drawColor: DrawColor.BLACK)),
                // Handles.
                RenderNode.leaf(new Line(parent.center, parent.secondHandlePos), new RenderOptions(drawColor: DrawColor.RED)),
                RenderNode.leaf(new Line(parent.center, parent.minuteHandlePos), new RenderOptions(drawColor: DrawColor.BLUE)),
                RenderNode.leaf(new Line(parent.center, parent.hourHandlePos), new RenderOptions(drawColor: DrawColor.GREEN))
        ]
        // TODO: Fix marks.
        nodes = nodes + hourMarks
        return RenderNode.node(
                // Center.
                new Point(pos: parent.center),
                // TODO: Make marks gray.
                new RenderOptions(drawColor: DrawColor.BLACK),
                // TODO: Rework tree structure - marks should be at bottom.
                nodes
        )
    }

    private getHourMarks() {
        if (hourMarks) {
            return hourMarks
        }
        Clock clock = (Clock) parent
        def hourMarks = (1..24).collect {
            def hourStep = clock.HOUR_CIRCLE_STEP * it
            def hourPosOnCircle = CircleOperations.getPointOnCircleInRadians(clock.circleDesc, hourStep)
            def line = new Line(clock.center + (clock.center - hourPosOnCircle) * 0.1, hourPosOnCircle)
            RenderNode.leaf(line, new RenderOptions(drawColor: DrawColor.YELLOW))
        }
        return hourMarks
    }
}
