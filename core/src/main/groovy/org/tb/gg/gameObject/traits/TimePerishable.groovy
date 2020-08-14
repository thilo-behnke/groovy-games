package org.tb.gg.gameObject.traits

import org.tb.gg.di.Inject
import org.tb.gg.global.DateProvider
import org.tb.gg.world.WorldStateProvider;

trait TimePerishable implements Perishable {
    @Inject private static WorldStateProvider worldStateProvider

    // Will be replaced by ast transformation.
    Boolean shouldPerish() {
        return false
    }

    private static long getTimestamp() {
        return worldStateProvider.get().currentLoopTimestamp
    }
}