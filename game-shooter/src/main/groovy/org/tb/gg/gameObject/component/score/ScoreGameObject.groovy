package org.tb.gg.gameObject.component.score

import org.tb.gg.config.ConfigurationService
import org.tb.gg.config.ConfigurationSettings
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.gameObject.component.guns.Gun
import org.tb.gg.gameObject.component.guns.GunWheel
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.factory.GameObjectBuilder
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.global.geom.Vector
import org.tb.gg.score.ScoreManager

class ScoreGameObject extends BaseGameObject {
    @Inject ScoreManager scoreManager
    @Inject GunWheel gunWheel
    @Inject static ConfigurationService configurationService

    int score
    Gun selectedGun

    static ScoreGameObject create() {
        Vector scorePos = new Vector(x: configurationService.resolution.getV1() - 120, y: configurationService.resolution.getV2() - 20)
        (ScoreGameObject) new GameObjectBuilder<ScoreGameObject>(ScoreGameObject)
            .setRenderComponent(new ScoreRenderComponent())
            .setBody(new ShapeBody(new Rect(scorePos, new Vector(x: 100, y: 100))))
            .build()
    }

    @Override
    void update(Long timestamp, Long delta) {
        score = scoreManager.score
        selectedGun = gunWheel.selectedGun()
    }
}
