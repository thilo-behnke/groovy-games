package org.tb.gg.gameObject.traits

import org.tb.gg.di.Inject
import org.tb.gg.gameObject.PerishCondition
import org.tb.gg.gameObject.Perishable
import org.tb.gg.world.WorldStateProvider;

@PerishCondition
trait TimePerishable implements Perishable {
    @Inject private WorldStateProvider worldStateProvider

    @SuppressWarnings('unused')
    long getTimestamp() {
        return worldStateProvider.get().currentLoopTimestamp
    }
}