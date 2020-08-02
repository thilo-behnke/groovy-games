package org.tb.gg.input.actions

import io.reactivex.rxjava3.core.Observable
import org.tb.gg.input.actions.InputActionProvider

class NoopInputActionProvider implements InputActionProvider {
    @Override
    Observable<Set<String>> activeActions$() {
        return Observable.just((Set<String>) [])
    }

    @Override
    Set<String> activeActions() {
        return (Set<String>) []
    }

    @Override
    void onInit() {

    }

    @Override
    void onDestroy() {

    }
}
