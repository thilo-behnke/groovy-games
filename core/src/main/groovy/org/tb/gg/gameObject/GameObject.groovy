package org.tb.gg.gameObject

import org.tb.gg.di.Inject
import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.gameObject.components.physics.PhysicsComponent
import org.tb.gg.gameObject.components.physics.ShapeBody
import org.tb.gg.gameObject.components.render.RenderComponent
import groovy.transform.EqualsAndHashCode
import org.tb.gg.gameObject.lifecycle.Lifecycle
import org.tb.gg.gameObject.traits.Perishable
import org.tb.gg.global.DateProvider
import org.tb.gg.global.geom.Vector
import org.tb.gg.renderer.destination.RenderDestination

@EqualsAndHashCode(includes='id')
class GameObject implements Updateable, Lifecycle, Perishable {
    Long id

    Vector orientation
    Boolean shouldBeDestroyed

    ShapeBody body
    RenderComponent renderComponent
    PhysicsComponent physicsComponent
    InputComponent inputComponent

    void setBody(ShapeBody body) {
        this.body = body
    }

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
    }

    @Override
    Boolean shouldPerish() {
        return shouldBeDestroyed
    }

    @Override
    void init() {
        renderComponent.init()
        inputComponent.init()
    }

    @Override
    void destroy() {
        renderComponent.destroy()
        inputComponent.destroy()
    }
}
