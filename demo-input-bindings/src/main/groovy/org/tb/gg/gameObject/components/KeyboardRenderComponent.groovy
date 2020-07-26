package org.tb.gg.gameObject.components

import groovy.util.logging.Log4j
import org.tb.gg.gameObject.Keyboard
import org.tb.gg.gameObject.components.RenderComponent
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.renderer.shape.Text

// TODO: Add Keyboard visualization to show pressed keys.
@Log4j
class KeyboardRenderComponent extends RenderComponent {
    @Override
    RenderNode getRenderNode() {
        def keyboard = (Keyboard) parent
        return RenderNode.leaf(new Text(pos: Vector.unitVector() * 500.0, text: keyboard.activeActions.join(', ')), new RenderOptions(drawColor: DrawColor.BLACK))
    }
}
