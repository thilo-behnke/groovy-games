package org.tb.gg.gameObject

import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.components.InputComponent
import org.tb.gg.gameObject.components.RenderComponent
import groovy.transform.EqualsAndHashCode
import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.renderer.destination.RenderDestination

@EqualsAndHashCode(includes='id')
abstract class GameObject implements Updateable, Lifecycle {
    // TODO: Automatically generate.
    Long id

    RenderComponent renderComponent
    InputComponent inputComponent

    void setInputComponent(InputComponent inputComponent) {
        this.inputComponent = inputComponent
    }

    void setRenderComponent(RenderComponent renderComponent) {
        renderComponent.setParent(this)
        this.renderComponent = renderComponent
    }

    void render(RenderDestination renderDestination) {
        renderComponent.render(renderDestination)
    }

    @Override
    void update(Long timestamp, Long delta) {
        def activeActions = inputComponent.getActiveActions()
        // TODO: Update based on active actions.
        // TODO: Integrate state machine.
    }

    @Override
    void onInit() {
        renderComponent.onInit()
        inputComponent.onInit()
    }

    @Override
    void onDestroy() {
        renderComponent.onDestroy()
        inputComponent.onDestroy()
    }
}
