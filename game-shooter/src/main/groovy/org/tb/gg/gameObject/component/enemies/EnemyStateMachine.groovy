package org.tb.gg.gameObject.component.enemies

import org.tb.gg.state.ActionState
import org.tb.gg.state.StateMachine

class EnemyStateMachine {
    static create(ActionState initialState) {
        return new StateMachine<ActionState>(initialState)
    }
}
