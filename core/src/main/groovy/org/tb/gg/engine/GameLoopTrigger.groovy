package org.tb.gg.engine

import org.tb.gg.di.definition.MultiInstanceService

interface GameLoopTrigger extends MultiInstanceService {
    onUpdate()
}