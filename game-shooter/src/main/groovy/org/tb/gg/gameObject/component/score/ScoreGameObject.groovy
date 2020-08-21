package org.tb.gg.gameObject.component.score

import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector

class ScoreGameObject extends BaseGameObject {
    static ScoreGameObject create() {
        (ScoreGameObject) new GameObjectBuilder<ScoreGameObject>(ScoreGameObject)
            .setBody(new ShapeBody(new Rect(new Vector(x: 500, y: 500), new Vector(x: 100, y: 100))))
            .build()
    }
}
