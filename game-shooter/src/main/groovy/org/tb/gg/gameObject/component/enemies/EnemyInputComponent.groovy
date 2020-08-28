package org.tb.gg.gameObject.component.enemies

import org.tb.gg.ai.AiInputActionProvider
import org.tb.gg.gameObject.component.enemies.state.EnemyIdleState
import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.gameObject.factory.InputComponentBuilder

class EnemyInputComponent {
    static create(EnemyGameObject parent) {
        def stateMachine = EnemyStateMachine.create(new EnemyIdleState(parent))
        def actionProvider = new AiInputActionProvider(stateMachine)
        new InputComponent(actionProvider)
    }
}
