package org.tb.gg.gameObject.component


import org.tb.gg.gameObject.CircleGameObject
import org.tb.gg.gameObject.components.RenderComponent
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.renderer.shape.Circle
import org.tb.gg.renderer.shape.InteractiveShape

class CircleRenderComponent extends RenderComponent {
    @Delegate
    InteractiveShape circle

    @Override
    RenderNode getRenderNode() {
        def circleGameObject = (CircleGameObject) parent
        circle = InteractiveShape.of(new Circle(center: circleGameObject.center, radius: circleGameObject.radius))
        return RenderNode.leaf(circle, new RenderOptions(drawColor: DrawColor.BLACK))
    }

    @Override
    void onInit() {
        def circleGameObject = (CircleGameObject) parent
        circle = InteractiveShape.of(new Circle(center: circleGameObject.center, radius: circleGameObject.radius))
    }

    @Override
    void onDestroy() {

    }
}
