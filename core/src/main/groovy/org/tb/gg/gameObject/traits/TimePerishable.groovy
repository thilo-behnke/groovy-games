package org.tb.gg.gameObject.traits;

trait TimePerishable {
    Boolean shouldPerish(Long timestamp, Long delta) {
        return false
    }
}