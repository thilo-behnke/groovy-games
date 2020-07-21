package input.actions

import groovy.util.logging.Log4j
import input.exception.IllegalKeyAssignmentException

import java.awt.event.KeyEvent

@Log4j
class ActionRegistry {

    private Set<String> actions
    private Map<KeyEvent, String> keyMappings

    void registerActions(Set<String> actions) {
        if (this.actions) {
            log.warn("Actions already registered, can't register again")
            return
        }
        this.actions = actions
    }

    void registerKeyMappings(Map<KeyEvent, String> keyMappings) throws IllegalKeyAssignmentException {
        throwIfActionsAreNotRegistered(keyMappings.values())
        this.keyMappings = keyMappings
    }

    void updateKeyMappings(Map<KeyEvent, String> mappingsToUpdate) throws IllegalKeyAssignmentException {
        throwIfActionsAreNotRegistered(keyMappings.values())
        this.keyMappings = this.keyMappings + mappingsToUpdate
    }

    void updateKeyMapping(KeyEvent keyEvent, String action) throws IllegalKeyAssignmentException {
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
