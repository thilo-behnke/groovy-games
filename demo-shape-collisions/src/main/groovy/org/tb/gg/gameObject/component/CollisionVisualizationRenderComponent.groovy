package org.tb.gg.gameObject.component


import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.renderer.options.DrawColor
import org.tb.gg.renderer.options.RenderOptions
import org.tb.gg.renderer.renderObjects.RenderNode

class CollisionVisualizationRenderComponent extends RenderComponent {

    @Override
    RenderNode getRenderNode() {
        def shape = parent.physicsComponent.getStructure()
        return RenderNode.leaf(shape, new RenderOptions(drawColor: parent.physicsComponent.collides ? DrawColor.RED : DrawColor.BLACK))
    }

    @Override
    void onInit() {

    }

    @Override
    void onDestroy() {

    }
}
