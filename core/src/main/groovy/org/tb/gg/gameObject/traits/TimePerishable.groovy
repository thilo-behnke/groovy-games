package org.tb.gg.gameObject.traits

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.Subject
import org.tb.gg.di.Inject
import org.tb.gg.gameObject.GameObject
import org.tb.gg.gameObject.GameObjectProvider

import java.util.function.Predicate

interface TimePerishable {
    boolean shouldPerish(Long timestamp, Long delta)
}