package org.tb.gg.input.actions

import io.reactivex.rxjava3.core.Observable
import org.tb.gg.engine.helper.Updateable
import org.tb.gg.gameObject.lifecycle.Lifecycle

trait InputActionProvider implements Lifecycle, Updateable {
    abstract Observable<Set<String>> activeActions$()
    abstract Set<String> activeActions()

    @Override
    void update(Long timestamp, Long delta) {}
}
