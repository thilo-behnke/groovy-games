package org.tb.gg.gameObject.services

import org.tb.gg.di.definition.Singleton
import org.tb.gg.gameObject.MovableGameObject
import org.tb.gg.gameObject.component.MovableGameObjectAction
import org.tb.gg.gameObject.component.MovableShapeInputComponent
import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.gameObject.components.input.NoopInputComponent
import org.tb.gg.gameObject.factory.InputComponentBuilder

class InputComponentProvider implements Singleton {
    private InputComponent inputComponent

    private MovableGameObject assignedMovableGameObject = null

    void setAssignedMovableGameObject(MovableGameObject gameObject) {
        if (inputComponent == null) {
            initializeInputComponent()
        }
        if (assignedMovableGameObject == gameObject) {
            return
        }
        this.assignedMovableGameObject?.setInputComponent(NoopInputComponent.get())
        gameObject.setInputComponent(inputComponent)
        this.assignedMovableGameObject = gameObject
    }

    private void initializeInputComponent() {
        inputComponent = new InputComponentBuilder()
                .setInputComponentClass(MovableShapeInputComponent)
                .setActions(
                        (HashSet<String>) MovableGameObjectAction.values()*.name(),
                )
                .setDefaultKeyMapping(
                        MovableGameObjectAction.values().collectEntries { it.keys.collectEntries() { key -> [(key): it.name()] } }
                )
                .build()
        inputComponent.init()
    }

    @Override
    void init() {
        // TODO: Can't use input component builder because the environment is not yet set. Can this be fixed?
    }

    @Override
    void destroy() {

    }
}
