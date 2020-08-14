package org.tb.gg.gameObject.traits

import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.world.WorldStateProvider

trait OutOfBoundsPerishable implements Perishable, GameObject {
    @Inject private WorldStateProvider worldStateProvider

    @Override
    Boolean shouldPerish() {
        System.println(isInBounds())
        return !isInBounds()
    }

    private boolean isInBounds() {
        worldStateProvider.get().bounds.isPointWithin(getBody().center)
    }
}