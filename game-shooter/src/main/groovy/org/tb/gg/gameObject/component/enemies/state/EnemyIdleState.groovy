package org.tb.gg.gameObject.component.enemies.state

import org.tb.gg.di.Inject
import org.tb.gg.gameObject.BaseGameObject
import org.tb.gg.state.ActionState
import org.tb.gg.state.State
import org.tb.gg.world.WorldStateProvider

class EnemyIdleState extends ActionState {
    @Inject
    WorldStateProvider worldStateProvider

    EnemyIdleState(BaseGameObject parent) {
        super(EnemyState.IDLE.toString(), parent)
    }

    @Override
    void enter() {
    }

    @Override
    State update() {
        def timeHasCome = worldStateProvider.get().currentLoopTimestamp % 132 == 0
        if (timeHasCome) {
            return new EnemyWanderingState(parent)
        }
        return this
    }

    @Override
    Set<String> getActions() {
        return []
    }

    @Override
    void exit() {

    }
}
