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

    private RenderNode minuteMarks
    private RenderNode hourMarks

    @Override
    RenderNode getRenderNode() {
        Clock parent = (Clock) parent
        // Clock marks.
        def nodes = []

        nodes << getClockCircle()
        nodes << getHandles()
        nodes << getHourMarks()
        nodes << getMinuteMarks()

        return RenderNode.node(
                nodes,
                // Center.
                new Point(pos: parent.center),
                // TODO: Make marks gray.
                new RenderOptions(drawColor: DrawColor.BLACK),
        )
    }

    private getClockCircle() {
        Clock parent = (Clock) parent
        RenderNode.leaf(new Circle(center: parent.center, radius: parent.radius), new RenderOptions(drawColor: DrawColor.BLACK))
    }

    private RenderNode getHandles() {
        Clock parent = (Clock) parent
        def handles = [
                RenderNode.leaf(new Line(parent.center, parent.secondHandlePos), new RenderOptions(drawColor: DrawColor.RED)),
                RenderNode.leaf(new Line(parent.center, parent.minuteHandlePos), new RenderOptions(drawColor: DrawColor.BLUE)),
                RenderNode.leaf(new Line(parent.center, parent.hourHandlePos), new RenderOptions(drawColor: DrawColor.GREEN))
        ]
        return RenderNode.node(handles)
    }

    private RenderNode getMinuteMarks() {
        if (minuteMarks) {
            return minuteMarks
        }
        Clock clock = (Clock) parent
        def minuteMarkNodes = (1..60).collect {
            def minuteStep = clock.MINUTE_CIRCLE_STEP * it
            def minutePosOnCircle = CircleOperations.getPointOnCircleInRadians(clock.circleDesc, minuteStep)
            def line = new Line(minutePosOnCircle + (clock.center - minutePosOnCircle) * 0.05, minutePosOnCircle)
            RenderNode.leaf(line, new RenderOptions(drawColor: DrawColor.YELLOW))
        }
        minuteMarks = RenderNode.node(minuteMarkNodes)
        minuteMarks
    }

    private RenderNode getHourMarks() {
        if (hourMarks) {
            return hourMarks
        }
        Clock clock = (Clock) parent
        def hourMarkNodes = (1..12).collect {
            def hourStep = clock.HOUR_CIRCLE_STEP * it
            def hourPosOnCircle = CircleOperations.getPointOnCircleInRadians(clock.circleDesc, hourStep)
            def line = new Line(hourPosOnCircle + (clock.center - hourPosOnCircle) * 0.2, hourPosOnCircle)
            RenderNode.leaf(line, new RenderOptions(drawColor: DrawColor.YELLOW))
        }
        hourMarks = RenderNode.node(hourMarkNodes)
        hourMarks
    }
}
