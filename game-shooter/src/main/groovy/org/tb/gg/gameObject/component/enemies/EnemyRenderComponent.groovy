package org.tb.gg.gameObject.component.enemies

import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode

class EnemyRenderComponent extends RenderComponent {
    @Override
    RenderNode getRenderNode() {
        EnemyGameObject enemyGameObject = (EnemyGameObject) parent;
        return RenderNode.leaf(
               enemyGameObject.body.shape,
               new RenderOptions(drawColor: enemyGameObject.wasHitRecently ? DrawColor.RED : DrawColor.BLACK)
        )
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
