package org.tb.gg.collision

class CollisionUtils {
    static class Range {
        BigDecimal min
        BigDecimal max

        static Range create(BigDecimal min, BigDecimal max) {
            def range = new Range()
            range.min = min < max ? min : max
            range.max = max >= min ? max : min
            return range
        }
    }

    static boolean doRangesOverlap(Range rangeA, Range rangeB) {
        rangeA.min >= rangeB.min && rangeA.min <= rangeB.max || rangeA.max >= rangeB.min && rangeA.max <= rangeB.max
    }
}
