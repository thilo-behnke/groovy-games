package org.tb.gg.gameObject.component

import org.tb.gg.collision.Collidable
import org.tb.gg.gameObject.CircleGameObject
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.InteractiveShape

class CollisionVisualizationRenderComponent extends RenderComponent {

    @Override
    RenderNode getRenderNode() {
        def circleGameObject = (CircleGameObject) parent
        def shape = circleGameObject.physicsComponent.getStructure()
        return RenderNode.leaf(shape, new RenderOptions(drawColor: circleGameObject.physicsComponent.collides ? DrawColor.RED : DrawColor.BLACK))
    }

    @Override
    void onInit() {

    }

    @Override
    void onDestroy() {

    }
}
