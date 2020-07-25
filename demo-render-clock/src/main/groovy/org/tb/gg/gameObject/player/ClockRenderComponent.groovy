package org.tb.gg.gameObject.player

import org.tb.gg.gameObject.components.RenderComponent
import org.tb.gg.global.geom.CircleOperations
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.renderer.shape.Circle
import org.tb.gg.renderer.shape.Line
import org.tb.gg.renderer.shape.Point

class ClockRenderComponent extends RenderComponent {

    private RenderNode minuteMarks
    private RenderNode hourMarks

    enum ClockRenderOrder {
        HANDLES, CENTER, CIRCLE, MARKS
    }

    @Override
    RenderNode getRenderNode() {
        Clock parent = (Clock) parent
        // Clock marks.
        def nodes = []

        nodes << getClockCircle()
        nodes << getHandles()
        nodes << getMarks()
        nodes << RenderNode.leaf(new Point(pos: parent.center), new RenderOptions(drawColor: DrawColor.BLACK), ClockRenderOrder.CENTER.ordinal())

        return RenderNode.node(
                nodes
        )
    }

    private getClockCircle() {
        Clock parent = (Clock) parent
        Circle circle = new Circle(center: parent.center, radius: parent.radius)
        RenderNode.leaf(circle, new RenderOptions(drawColor: DrawColor.BLACK), ClockRenderOrder.CIRCLE.ordinal())
    }

    private RenderNode getHandles() {
        Clock parent = (Clock) parent
        def handles = [
                RenderNode.leaf(new Line(parent.center, parent.secondHandlePos), new RenderOptions(drawColor: DrawColor.RED), 0),
                RenderNode.leaf(new Line(parent.center, parent.minuteHandlePos).scaleFromStart(0.8), new RenderOptions(drawColor: DrawColor.BLUE), 1),
                RenderNode.leaf(new Line(parent.center, parent.hourHandlePos).scaleFromStart(0.6), new RenderOptions(drawColor: DrawColor.GREEN), 2)
        ]
        return RenderNode.node(handles, ClockRenderOrder.HANDLES.ordinal())
    }

    private RenderNode getMarks() {
        def minuteMarks = getMinuteMarks()
        def hourMarks = getHourMarks()
        def markNodes = [minuteMarks, hourMarks]
        RenderNode.node(markNodes, ClockRenderOrder.MARKS.ordinal())
    }

    private RenderNode getMinuteMarks() {
        if (minuteMarks) {
            return minuteMarks
        }
        Clock clock = (Clock) parent
        def minuteMarkNodes = (1..60).collect {
            def minuteStep = clock.MINUTE_CIRCLE_STEP * it
            def minutePosOnCircle = CircleOperations.getPointOnCircleInRadians(clock.circleDesc, minuteStep)
            def line = new Line(clock.center, minutePosOnCircle)
            line.scaleFromEnd(0.05)
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
            def line = new Line(clock.center, hourPosOnCircle)
            line.scaleFromEnd(0.2)
            RenderNode.leaf(line, new RenderOptions(drawColor: DrawColor.YELLOW))
        }
        hourMarks = RenderNode.node(hourMarkNodes)
        hourMarks
    }
}
