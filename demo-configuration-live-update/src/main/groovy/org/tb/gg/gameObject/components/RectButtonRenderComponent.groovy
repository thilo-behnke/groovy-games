package org.tb.gg.gameObject.components

import org.tb.gg.gameObject.RectButton
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.renderer.shape.InteractiveShape
import org.tb.gg.renderer.shape.Rect
import org.tb.gg.renderer.shape.Text

class RectButtonRenderComponent extends RenderComponent {

    private InteractiveShape rect

    RectButtonRenderComponent(Vector pos, Vector dim) {
        rect = InteractiveShape<Rect>.of(new Rect(pos, dim))
        rect.init()
    }

    @Override
    RenderNode getRenderNode() {
        def button = (RectButton) parent
        def shape = (Rect) rect.getShape()
        shape.topLeft = button.pos
        shape.dim = button.dim

        def boundary = RenderNode.leaf(rect, new RenderOptions(drawColor: rect.isMouseInShape ? DrawColor.RED : DrawColor.YELLOW))
        def text = RenderNode.leaf(new Text(pos: shape.topLeft + shape.dim * new Vector(x: 0.3, y: 0.5) * Vector.invertYVector(), text: 'Click Me'))
        return RenderNode.node([boundary, text])
    }
}
