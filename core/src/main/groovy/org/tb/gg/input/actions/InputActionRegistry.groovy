package org.tb.gg.input.actions

import groovy.util.logging.Log4j
import org.tb.gg.input.Key
import org.tb.gg.input.exception.IllegalKeyAssignmentException

@Log4j
class InputActionRegistry {

    private Set<String> actions
    private Map<Key, String> keyMappings = new HashMap<>()

    Map<Key, String> getKeyMappings() {
        return keyMappings
    }

    InputActionRegistry(Set<String> actions) {
        this.actions = actions
    }

    void overrideKeyMappings(Map<Key, String> keyMappings) throws IllegalKeyAssignmentException {
        throwIfActionsAreNotRegistered(keyMappings.values())
        this.keyMappings.clear()
        this.keyMappings.putAll(keyMappings)
    }

    void updateKeyMappings(Map<Key, String> mappingsToUpdate) throws IllegalKeyAssignmentException {
        throwIfActionsAreNotRegistered(keyMappings.values())
        this.keyMappings.putAll(mappingsToUpdate)
    }

    void updateKeyMapping(Key keyEvent, String action) throws IllegalKeyAssignmentException {
        throwIfActionIsNotRegistered(action)
        this.keyMappings.put(keyEvent, action)
    }

    private throwIfActionsAreNotRegistered(Collection<String> actions) {
        def unregisteredActions = actions - this.actions
        if (unregisteredActions.size() > 0) {
            throw new IllegalKeyAssignmentException("Tried to assign key to unregistered action(s): ${unregisteredActions.join(", ")}")
        }
    }

    private throwIfActionIsNotRegistered(String action) {
        def isActionUnregistered = !actions.find { it == action }
        if (isActionUnregistered) {
            throw new IllegalKeyAssignmentException("Tried to assign key to unregistered action: ${action}")
        }
    }
}
