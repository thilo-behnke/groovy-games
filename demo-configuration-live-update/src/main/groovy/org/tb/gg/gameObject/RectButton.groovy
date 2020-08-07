package org.tb.gg.gameObject

import org.tb.gg.config.ConfigurationService
import org.tb.gg.config.ConfigurationSettings
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.components.RectButtonRenderComponent
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.shape.InteractiveGameObject
import org.tb.gg.global.geom.Vector

class RectButton extends GameObject {
    Vector pos
    Vector dim

    @Inject ConfigurationService configurationService

    static RectButton create(Vector pos, Vector dim) {
       def button = InteractiveGameObject<RectButton>.of(new RectButton(pos: pos, dim: dim))
        button.setRenderComponent(new RectButtonRenderComponent(pos, dim))
        button.setInputComponent(new NoopInputComponent())

        button.mouseClicks.subscribe {
            switch(configurationService.windowMode) {
                case ConfigurationSettings.WindowMode.WINDOWED:
                    configurationService.setFullScreen()
                    break
                case ConfigurationSettings.WindowMode.FULLSCREEN:
                    configurationService.setWindowed()
                    break
                default:
                    break
            }
        }

        return button as RectButton
    }
}
