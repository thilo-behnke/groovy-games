package org.tb.gg.gameObject.components

import groovy.util.logging.Log4j
import org.tb.gg.gameObject.Keyboard
import org.tb.gg.gameObject.components.RenderComponent
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.renderer.shape.Text

import java.awt.Rectangle

// TODO: Add Keyboard visualization to show pressed keys.
@Log4j
class KeyboardRenderComponent extends RenderComponent {

    private final static TITLE_POS_Y = -10
    private final static ACTIVE_ACTIONS_POS_Y = -30

    Vector pos

    @Override
    RenderNode getRenderNode() {
        RenderNode.node([
                createTitleText(),
                createActiveActionsText()
        ])
    }

    private createTitleText() {
        def keyboard = (Keyboard) parent
        RenderNode.leaf(
                new Text(pos: pos + new Vector(x: 0, y: TITLE_POS_Y), text: "Keyboard: ${keyboard.id}"),
                new RenderOptions(drawColor: DrawColor.BLACK)
        )
    }

    private createActiveActionsText() {
        def keyboard = (Keyboard) parent
        RenderNode.leaf(
                new Text(pos: pos + new Vector(x: 0, y: ACTIVE_ACTIONS_POS_Y), text: keyboard.activeActions.join(', ')), new RenderOptions(drawColor: DrawColor.BLACK)
        )
    }
}
