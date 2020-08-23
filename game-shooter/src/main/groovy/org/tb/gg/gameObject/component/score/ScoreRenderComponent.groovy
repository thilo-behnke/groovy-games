package org.tb.gg.gameObject.component.score

import org.tb.gg.gameObject.component.guns.Gun
import org.tb.gg.gameObject.components.render.RenderComponent
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.shape.Text
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.renderObjects.RenderNode

class ScoreRenderComponent extends RenderComponent {
    private final Vector scoreBoxShift = new Vector(x: 10, y: -25)
    private final Vector selectedGunBoxShift = new Vector(x: 10, y: -45)

    private RenderNode scoreRender
    private int lastScore
    private Gun lastSelectedGun

    @Override
    RenderNode getRenderNode() {
        def bodyRect = (Rect) parent.body.shape
        def scoreGameObject = (ScoreGameObject) parent

        if (scoreRender && lastScore == scoreGameObject.score && lastSelectedGun == scoreGameObject.selectedGun) {
            return scoreRender
        }

        lastScore = scoreGameObject.score
        lastSelectedGun = scoreGameObject.selectedGun

        scoreRender = RenderNode.node([
                RenderNode.leaf(new Text(bodyRect.topLeft + scoreBoxShift, bodyRect.dim, "Score: ${scoreGameObject.score}")),
                RenderNode.leaf(new Text(bodyRect.topLeft + selectedGunBoxShift, bodyRect.dim, "Gun: ${scoreGameObject.selectedGun.props.gunType}"))
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
