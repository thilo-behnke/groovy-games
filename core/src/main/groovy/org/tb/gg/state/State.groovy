package org.tb.gg.state

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['name'])
abstract class State {
    String name

    State(String name) {
        this.name = name
    }

    abstract void enter()

    abstract State update()

    abstract void exit()
}
