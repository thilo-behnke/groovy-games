package org.tb.gg.gameObject.traits;

trait TimePerishable {
    boolean shouldPerish(Long timestamp, Long delta) {
        return false
    }
}