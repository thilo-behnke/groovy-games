package org.tb.gg.ai

import io.reactivex.rxjava3.core.Observable
import org.tb.gg.input.actions.InputActionProvider
import org.tb.gg.state.ActionState
import org.tb.gg.state.StateMachine

abstract class AiInputActionProvider implements InputActionProvider {

    private StateMachine<ActionState> stateMachine

    AiInputActionProvider(StateMachine stateMachine) {
        this.stateMachine = stateMachine
    }

    @Override
    Observable<Set<String>> activeActions$() {
        return Observable.just(activeActions())
    }

    @Override
    Set<String> activeActions() {
        return stateMachine.activeState.actions.collect { it.toString() }.toSet()
    }
}