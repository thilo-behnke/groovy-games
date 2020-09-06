package org.tb.gg.gameObject.component.enemies

import org.tb.gg.ai.AiInputActionProvider
import org.tb.gg.gameObject.component.enemies.state.EnemyIdleState
import org.tb.gg.gameObject.components.input.InputComponent
import org.tb.gg.gameObject.factory.InputComponentBuilder
import org.tb.gg.input.actions.InputActionProvider
import org.tb.gg.state.StateMachine

class EnemyInputComponent extends InputComponent {

    StateMachine stateMachine

    private EnemyInputComponent(InputActionProvider inputActionProvider, StateMachine stateMachine) {
        super(inputActionProvider)
        this.stateMachine = stateMachine
    }

    static create(EnemyGameObject parent) {
        def stateMachine = EnemyStateMachine.create(new EnemyIdleState(parent))
        def actionProvider = new AiInputActionProvider(stateMachine)

        new EnemyInputComponent(actionProvider, stateMachine)
    }
}
