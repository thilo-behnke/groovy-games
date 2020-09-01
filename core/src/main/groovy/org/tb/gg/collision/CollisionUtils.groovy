package org.tb.gg.collision

import org.tb.gg.gameObject.shape.Line
import org.tb.gg.global.geom.Vector

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

        static Range projectLine(Line line, Vector onto) {
            def ontoNormalized = onto.normalize()
            def dotLineStart = line.start.dot(ontoNormalized)
            def dotLineEnd = line.end.dot(ontoNormalized)
            create(dotLineStart, dotLineEnd)
        }

        static Range hullFrom(Range a, Range b) {
            def min = a.min < b.min ? a.min : b.min
            def max = a.max > b.max ? a.max : b.max
            return new Range(min: min, max: max)
        }
    }

    static boolean doRangesOverlap(Range rangeA, Range rangeB) {
        rangeA.min >= rangeB.min && rangeA.min <= rangeB.max || rangeA.max >= rangeB.min && rangeA.max <= rangeB.max
                || rangeB.min >= rangeA.min && rangeB.min <= rangeA.max || rangeB.max >= rangeA.min && rangeB.max <= rangeA.max
    }
}
