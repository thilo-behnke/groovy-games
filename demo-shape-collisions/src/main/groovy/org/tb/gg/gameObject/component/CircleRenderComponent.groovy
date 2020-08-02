package org.tb.gg.gameObject.component

import org.tb.gg.gameObject.CircleGameObject
import org.tb.gg.gameObject.components.RenderComponent
import org.tb.gg.renderer.renderObjects.RenderNode
import org.tb.gg.renderer.shape.Circle

class CircleRenderComponent extends RenderComponent {

    @Override
    RenderNode getRenderNode() {
        def circle = (CircleGameObject) parent
        return RenderNode.leaf(new Circle(center: parent.center, radius: parent.radius))
    }

    @Override
    void onInit() {

    }

    @Override
    void onDestroy() {

    }
}
