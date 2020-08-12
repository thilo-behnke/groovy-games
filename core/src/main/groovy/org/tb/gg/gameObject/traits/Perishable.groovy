package org.tb.gg.gameObject.traits

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.Subject
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider

import java.util.function.Predicate

trait Perishable {

    @Inject GameObjectProvider gameObjectProvider
    boolean readyToPerish = false

    public <T extends GameObject> void checkIfShouldBePerished(Predicate<T> predicate) {
        System.println(predicate.test())
        if (predicate.test()) {
            setReadyToPerish(true)
        }
    }

    abstract boolean shouldBePerished(Long timestamp)
}