package org.tb.gg.gameObject.components

import org.tb.gg.config.ConfigurationService
import org.tb.gg.config.ConfigurationSettings
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.RectButton
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.gameObject.shape.InteractiveGameObject
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Text

class RectButtonRenderComponent extends RenderComponent {

    @Inject
    private ConfigurationService configurationService

    private Rect rect

    RectButtonRenderComponent(Vector pos, Vector dim) {
        rect = new Rect(pos, dim)
    }

    @Override
    void onInit() {
        rect.onInit()
    }

    @Override
    void onDestroy() {
        rect.onDestroy()
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
