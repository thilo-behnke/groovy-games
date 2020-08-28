package org.tb.gg.state

class StateMachine {
    private State activeState

    StateMachine(State initialState) {
        setActiveState(initialState)
    }

    void update() {
        State newState = activeState.update()
        if (activeState == newState) {
            return
        }
        setActiveState(newState)
    }

    void setActiveState(State state) {
        activeState.exit()
        activeState = state
        activeState.enter()
    }

    State getActiveState(State state) {
        activeState
    }
}
