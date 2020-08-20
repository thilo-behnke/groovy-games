package org.tb.gg.gameObject

import io.reactivex.rxjava3.disposables.Disposable
import org.tb.gg.config.ConfigurationService
import org.tb.gg.config.ConfigurationSettings
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.components.RectButtonRenderComponent
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.shape.Rect
import org.tb.gg.gameObject.traits.InteractiveBody
import org.tb.gg.global.geom.Vector

class RectButton extends BaseGameObject implements InteractiveBody {
    Vector pos
    Vector dim

    @Inject
    ConfigurationService configurationService

    private Disposable mouseClickDisposable

    RectButton(Vector pos, Vector dim) {
        this.pos = pos
        this.dim = dim
    }

    @Override
    void init() {
        super.init()

        mouseClickDisposable = mouseClicks.subscribe {
            switch (configurationService.windowMode) {
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
    }

    @Override
    void destroy() {
        super.destroy()

        mouseClickDisposable.dispose()
    }

    static RectButton create(Vector pos, Vector dim) {
        def button = new RectButton(pos, dim)
        button.setBody(new ShapeBody(new Rect(pos, dim)))
        button.setRenderComponent(new RectButtonRenderComponent())
        button.setInputComponent(NoopInputComponent.get())
        return button
    }
}
