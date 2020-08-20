package org.tb.gg.gameObject.component.player


import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.gameObject.shape.Line
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode

class PlayerRenderComponent extends RenderComponent {

    @Override
    RenderNode getRenderNode() {
        def player = (PlayerGameObject) parent
        def playerBody = parent.body.getStructure()
        def orientationLine = new Line(playerBody.center, playerBody.center + player.orientation)

        return RenderNode.node([
                RenderNode.leaf(
                        playerBody,
                        new RenderOptions(drawColor: parent.physicsComponent.collides ? DrawColor.RED : DrawColor.BLACK)
                ),
                RenderNode.leaf(
                        orientationLine,
                        new RenderOptions(drawColor: DrawColor.RED)
                )
        ])
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
