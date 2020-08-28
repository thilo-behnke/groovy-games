package org.tb.gg.state

import org.tb.gg.gameObject.BaseGameObject

abstract class ActionState extends State {
    BaseGameObject parent

    ActionState(String name, BaseGameObject parent) {
        super(name)
        this.parent = parent
    }

    abstract Set<String> getActions()
}
