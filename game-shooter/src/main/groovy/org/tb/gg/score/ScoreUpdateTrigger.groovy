package org.tb.gg.score

import org.tb.gg.di.Inject
import org.tb.gg.engine.GameLoopTrigger
import org.tb.gg.events.EventManager
import org.tb.gg.events.event.PerishEvent

class ScoreUpdateTrigger implements GameLoopTrigger {
    @Inject
    EventManager eventManager
    @Inject
    ScoreManager scoreManager

    @Override
    def onUpdate() {
        def perishEvents = eventManager.getEventsByType(PerishEvent)
        int score = perishEvents.collect { 100 }.sum() as int
        return scoreManager + score
    }

    @Override
    void init() {

    }

    @Override
    void destroy() {

    }
}
