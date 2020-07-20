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
        def nodes = [
                // Clock circle.
                RenderNode.leaf(new Circle(center: parent.center, radius: parent.radius), new RenderOptions(drawColor: DrawColor.BLACK)),
                // Handles.
                RenderNode.leaf(new Line(parent.center, parent.secondHandlePos), new RenderOptions(drawColor: DrawColor.RED)),
                RenderNode.leaf(new Line(parent.center, parent.minuteHandlePos), new RenderOptions(drawColor: DrawColor.BLUE)),
                RenderNode.leaf(new Line(parent.center, parent.hourHandlePos), new RenderOptions(drawColor: DrawColor.GREEN))
        ]

        def hourMarks = getHourMarks()
        def minuteMarks = getMinuteMarks()
        nodes = nodes + hourMarks + minuteMarks
        return RenderNode.node(
                // Center.
                new Point(pos: parent.center),
                // TODO: Make marks gray.
                new RenderOptions(drawColor: DrawColor.BLACK),
                // TODO: Rework tree structure - marks should be at bottom.
                nodes
        )
    }

    private getMinuteMarks() {
        if (minuteMarks) {
            return minuteMarks
        }
        Clock clock = (Clock) parent
        minuteMarks = (1..60).collect {
            def minuteStep = clock.MINUTE_CIRCLE_STEP * it
            def minutePosOnCircle = CircleOperations.getPointOnCircleInRadians(clock.circleDesc, minuteStep)
            def line = new Line(minutePosOnCircle + (clock.center - minutePosOnCircle) * 0.05, minutePosOnCircle)
            RenderNode.leaf(line, new RenderOptions(drawColor: DrawColor.YELLOW))
        }
        return hourMarks
    }

    private getHourMarks() {
        if (hourMarks) {
            return hourMarks
        }
        Clock clock = (Clock) parent
        hourMarks = (1..12).collect {
            def hourStep = clock.HOUR_CIRCLE_STEP * it
            def hourPosOnCircle = CircleOperations.getPointOnCircleInRadians(clock.circleDesc, hourStep)
            def line = new Line(hourPosOnCircle + (clock.center - hourPosOnCircle) * 0.2, hourPosOnCircle)
            RenderNode.leaf(line, new RenderOptions(drawColor: DrawColor.YELLOW))
        }
        return hourMarks
    }
}
