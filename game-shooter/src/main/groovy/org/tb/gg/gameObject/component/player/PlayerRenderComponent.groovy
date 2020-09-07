package org.tb.gg.gameObject.component.player


import org.tb.gg.gameObject.components.render.DefaultRenderComponent
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode

class PlayerRenderComponent extends DefaultRenderComponent {
    @Override
    protected List<RenderNode> getDebugNodes() {
        def player = (PlayerGameObject) parent
        def playerBoundingRect = parent.body.getStructure()
        def orientationLine = new Line(playerBoundingRect.center, playerBoundingRect.center + player.orientation)
        return [RenderNode.leaf(
                orientationLine,
                new RenderOptions(drawColor: DrawColor.RED)
        )]
    }
}
