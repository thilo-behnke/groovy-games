package org.tb.gg.gameObject.component

import org.tb.gg.collision.Collidable
import org.tb.gg.gameObject.CircleGameObject
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.gameObject.shape.Circle
import org.tb.gg.gameObject.shape.InteractiveShape

class CircleRenderComponent extends RenderComponent {
    InteractiveShape circle

    @Override
    RenderNode getRenderNode() {
        def circleGameObject = (CircleGameObject) parent
        circle = (InteractiveShape) circleGameObject.physicsComponent.getStructure()
        return RenderNode.leaf(circle, new RenderOptions(drawColor: circleGameObject.physicsComponent.collides ? DrawColor.RED : DrawColor.BLACK))
    }

    @Override
    void onInit() {

    }

    @Override
    void onDestroy() {

    }
}
