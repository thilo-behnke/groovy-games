package org.tb.gg.gameObject.traits

import io.reactivex.rxjava3.core.Observable
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider

trait Perishable {

    @Inject GameObjectProvider gameObjectProvider

    abstract boolean shouldPerish(Long timestamp)

    void perish(GameObject gameObject) {
        gameObjectProvider.removeGameObject(gameObject)
    }
}