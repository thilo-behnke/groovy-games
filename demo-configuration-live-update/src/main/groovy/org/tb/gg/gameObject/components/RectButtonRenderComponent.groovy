package org.tb.gg.gameObject.components

import org.tb.gg.gameObject.RectButton
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Text

class RectButtonRenderComponent extends RenderComponent {

    RectButtonRenderComponent() {
    }

    @Override
    RenderNode getRenderNode() {
        def button = (RectButton) parent
        def rect = (Rect) parent.body.getStructure()

        def boundary = RenderNode.leaf(rect, new RenderOptions(drawColor: button.isMouseInShape ? DrawColor.RED : DrawColor.YELLOW))
        def text = new Text(
                rect.topLeft + rect.dim * new Vector(x: 0.3, y: 0.5) * Vector.invertYVector(),
                rect.dim,
                'Click Me')
        def textNode = RenderNode.leaf(text)
        return RenderNode.node([boundary, textNode])
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
