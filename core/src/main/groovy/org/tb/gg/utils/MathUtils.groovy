package org.tb.gg.utils

class MathUtils {
    static BigDecimal normalizeInRange(BigDecimal n, BigDecimal from, BigDecimal to) {
        if (from > to) {
            throw new IllegalArgumentException("from (${from}) must be larger than to (${to})".toString())
        }
        if (n <= from) {
            return from
        } else if (n >= to) {
            return to
        } else {
            return n
        }
    }
}
