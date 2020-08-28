package org.tb.gg.state

abstract class ActionState extends State {
    List<String> actions

    ActionState(String name, List<String> actions) {
        super(name)
        this.actions = actions
    }
}
