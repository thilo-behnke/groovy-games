package org.tb.gg.gameObject


import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.gameObject.components.physics.Body
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.gameObject.components.render.RenderComponent
import groovy.transform.EqualsAndHashCode
import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.renderer.destination.RenderDestination
import org.tb.gg.gameObject.shape.Shape

@EqualsAndHashCode(includes='id')
class GameObject implements Updateable, Lifecycle {
    // TODO: Automatically generate.
    Long id

    RenderComponent renderComponent
    PhysicsComponent physicsComponent
    InputComponent inputComponent

    void setInputComponent(InputComponent inputComponent) {
        this.inputComponent = inputComponent
    }

    void setPhysicsComponent(PhysicsComponent physicsComponent) {
        physicsComponent.setParent(this)
        this.physicsComponent = physicsComponent
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
