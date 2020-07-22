package input.actions

import input.keyEvent.KeyEventSubject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

class InputActionProvider {
    private InputActionRegistry actionRegistry
    private KeyEventSubject keyEventSubject

    private BehaviorSubject<Set<String>> activeActionsSource

    InputActionProvider(InputActionRegistry actionRegistry, KeyEventSubject keyEventSubject) {
        this.actionRegistry = actionRegistry
        this.keyEventSubject = keyEventSubject

        activeActionsSource = BehaviorSubject.createDefault((Set<String>) [])
    }

    void register() {
        this.keyEventSubject.pressedKeys()
                .map { keys ->
                    def mappedToActions = keys
                            .collect { key ->
                                actionRegistry.getKeyMappings().find { mapping -> mapping.key == key }?.value
                            }
                            .find { it != null }
                    mappedToActions.toSet()
                }
                .distinctUntilChanged()
                .subscribe { activeActionsSource.onNext(it) }
    }

    Observable<Set<String>> activeActions$() {
        activeActionsSource
    }

    Set<String> activeActions() {
        activeActionsSource.getValue()
    }
}
