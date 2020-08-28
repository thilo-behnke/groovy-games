package org.tb.gg.state

class StateMachine<T extends State> {
    private T activeState

    StateMachine(T initialState) {
        setActiveState(initialState)
    }

    void update() {
        State newState = activeState.update()
        if (activeState == newState) {
            return
        }
        setActiveState((T) newState)
    }

    void setActiveState(T state) {
        activeState.exit()
        activeState = (T) state
        activeState.enter()
    }

    T getActiveState() {
        activeState
    }
}
