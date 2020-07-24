package input.actions

import groovy.util.logging.Log4j
import input.Key
import input.exception.IllegalKeyAssignmentException
import input.keyEvent.KeyEvent

@Log4j
class InputActionRegistry {

    private Set<String> actions
    private Map<Key, String> keyMappings

    Map<Key, String> getKeyMappings() {
        return keyMappings
    }

    void registerActions(Set<String> actions) {
        if (this.actions) {
            log.warn("Actions already registered, can't register again")
            return
        }
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
