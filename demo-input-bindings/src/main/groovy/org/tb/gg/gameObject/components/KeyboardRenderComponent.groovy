package org.tb.gg.gameObject.components

import groovy.util.logging.Log4j
import org.tb.gg.gameObject.Keyboard
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Text

@Log4j
class KeyboardRenderComponent extends RenderComponent {

    private final static FRAME_START = new Vector(x: -10, y: 0)
    private final static FRAME_DIM = new Vector(x: 100, y: 50)
    private final static TITLE_POS_Y = -20
    private final static ACTIVE_ACTIONS_POS_Y = -40

    Vector pos

    @Override
    RenderNode getRenderNode() {
        RenderNode.node([
                createFrame(),
                createTitleText(),
                createActiveActionsText()
        ])
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }

    private createFrame() {
        RenderNode.leaf(
                new Rect(pos + FRAME_START, FRAME_DIM),
                new RenderOptions(drawColor: DrawColor.BLACK)
        )
    }

    private createTitleText() {
        def keyboard = (Keyboard) parent
        RenderNode.leaf(
                new Text(
                        pos + new Vector(x: 0, y: TITLE_POS_Y),
                        FRAME_DIM,
                        "Player: ${keyboard.id}".toString()
                ),
                new RenderOptions(drawColor: DrawColor.BLACK)
        )
    }

    private createActiveActionsText() {
        def keyboard = (Keyboard) parent
        RenderNode.leaf(
                new Text(
                        pos + new Vector(x: 0, y: ACTIVE_ACTIONS_POS_Y),
                        FRAME_DIM,
                        keyboard.activeActions.join(', ')),
                new RenderOptions(drawColor: DrawColor.BLACK)
        )
    }
}
