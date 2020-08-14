package org.tb.gg.gameObject.traits

import org.tb.gg.di.Inject
import org.tb.gg.global.DateProvider;

trait TimePerishable {
    @Inject private static DateProvider dateProvider

    Boolean shouldPerish(Long timestamp, Long delta) {
        return false
    }

    private static long now() {
        return dateProvider.now()
    }
}