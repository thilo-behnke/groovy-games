package input.actions

import input.Key
import input.exception.IllegalKeyAssignmentException
import input.keyEvent.KeyEventSubject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import service.Service

class InputActionProvider implements Service {
    @Delegate
    private final InputActionRegistry actionRegistry
    private final KeyEventSubject keyEventSubject

    private final BehaviorSubject<Set<String>> activeActionsSource

    private Disposable pressedKeyDisposable

    InputActionProvider(InputActionRegistry actionRegistry, KeyEventSubject keyEventSubject) {
        this.actionRegistry = actionRegistry
        this.keyEventSubject = keyEventSubject

        activeActionsSource = BehaviorSubject.createDefault((Set<String>) [])
    }

    Observable<Set<String>> activeActions$() {
        activeActionsSource
    }

    Set<String> activeActions() {
        activeActionsSource.getValue()
    }

    void init() {
        pressedKeyDisposable = this.keyEventSubject.pressedKeys()
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

    void destroy() {
        pressedKeyDisposable.dispose()
    }
}
