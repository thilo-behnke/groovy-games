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

    private Rect rect

    RectButtonRenderComponent(Vector pos, Vector dim) {
        rect = new Rect(pos, dim)
    }

    @Override
    RenderNode getRenderNode() {
        def button = (RectButton) parent
        rect.topLeft = button.pos
        rect.dim = button.dim

        def boundary = RenderNode.leaf(this.rect, new RenderOptions(drawColor: button.isMouseInShape ? DrawColor.RED : DrawColor.YELLOW))
        // TODO: Fix
        def text = RenderNode.leaf(new Text(pos: rect.topLeft + rect.dim * new Vector(x: 0.3, y: 0.5) * Vector.invertYVector(), text: 'Click Me'))
        return RenderNode.node([boundary, text])
    }

    @Override
    void onInit() {

    }

    @Override
    void onDestroy() {

    }
}
