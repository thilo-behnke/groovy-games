package org.tb.gg.gameObject.component.score


import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Text
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.renderObjects.RenderNode

class ScoreRenderComponent extends RenderComponent {
    private final Vector textBoxShift = new Vector(x: 10, y: -25)

    private RenderNode scoreRender
    private int lastScore

    @Override
    RenderNode getRenderNode() {
        def bodyRect = (Rect) parent.body.shape
        def scoreGameObject = (ScoreGameObject) parent

        if (scoreRender && lastScore == scoreGameObject.score) {
            return scoreRender
        }

        lastScore = scoreGameObject.score
        scoreRender = RenderNode.node([
                RenderNode.leaf(new Text(bodyRect.topLeft + textBoxShift, bodyRect.dim, "Score: ${scoreGameObject.score}"))
        ], bodyRect)

        return scoreRender
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
