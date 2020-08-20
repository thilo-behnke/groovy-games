package org.tb.gg.spawner

import org.tb.gg.di.definition.MultiInstanceService
import org.tb.gg.gameObject.GameObject

interface Spawner<T extends GameObject> extends MultiInstanceService {
    Set<T> spawn()
}