package org.tb.gg.gameObject.components

import org.tb.gg.gameObject.Button
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.renderer.shape.InteractiveShape
import org.tb.gg.renderer.shape.Rect

class ButtonRenderComponent extends RenderComponent {

    private InteractiveShape interactiveShape

    ButtonRenderComponent(Vector pos, Vector dim) {
        interactiveShape = InteractiveShape<Rect>.of(new Rect(pos, dim))
    }

    @Override
    RenderNode getRenderNode() {
        def button = (Button) parent
        def shape = (Rect) interactiveShape.getShape()
        shape.topLeft = button.pos
        shape.dim = button.dim
        return RenderNode.leaf(interactiveShape, new RenderOptions(drawColor: interactiveShape.isMouseInShape ? DrawColor.RED : DrawColor.YELLOW))
    }
}
