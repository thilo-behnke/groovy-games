package org.tb.gg.input.actions

import groovy.util.logging.Log4j
import org.tb.gg.di.definition.Service
import org.tb.gg.input.Key
import org.tb.gg.input.exception.IllegalKeyAssignmentException
import org.tb.gg.input.keyEvent.KeyEventSubject
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

@Log4j
class KeyPressInputActionProvider implements InputActionProvider, Service {
    private final InputActionRegistry actionRegistry
    private final KeyEventSubject keyEventSubject

    private final BehaviorSubject<Set<String>> activeActionsSource

    private Disposable pressedKeyDisposable

    KeyPressInputActionProvider(InputActionRegistry actionRegistry, KeyEventSubject keyEventSubject) {
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

    void overrideKeyMappings(Map<Key, String> keyMappings) throws IllegalKeyAssignmentException {
        // TODO: Handle removed keys, etc...
        actionRegistry.overrideKeyMappings(keyMappings)
        keyEventSubject.listenToKeys(*keyMappings.keySet().toList())
    }

    void init() {
        pressedKeyDisposable = this.keyEventSubject.pressedKeys()
                .map { keys ->
                    def mappedToActions = keys
                            .collect { key ->
                                actionRegistry.getKeyMappings().find { mapping -> mapping.key == key }?.value
                            }
                            .findAll { it != null }
                    if (!mappedToActions) {
                        return new HashSet<>()
                    }
                    mappedToActions.toSet()
                }
                .distinctUntilChanged()
                .subscribe { activeActionsSource.onNext(it) }
    }

    void destroy() {
        pressedKeyDisposable.dispose()
    }
}
