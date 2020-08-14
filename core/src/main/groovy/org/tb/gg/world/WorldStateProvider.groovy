package org.tb.gg.world

import org.tb.gg.di.definition.Singleton

class WorldStateProvider implements Singleton {
    private WorldState worldState

    WorldState get() {
        return worldState
    }

    void set(WorldState worldState) {
        this.worldState = worldState
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
