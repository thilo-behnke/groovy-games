package org.tb.gg.global.math

import ch.obermuhlner.math.big.BigDecimalMath

import java.math.MathContext
import java.math.RoundingMode

class MathConstants {
    final static ctx = new MathContext(5, RoundingMode.HALF_UP)
    final static PI = BigDecimal.valueOf(Math.PI)
    final static HALF_PI = (PI / 2).round(MathConstants.ctx)
    final static QUARTER_PI = (PI / 4).round(MathConstants.ctx)

    static pi(BigDecimal div) {
        if (!div) {
            return PI.round(MathConstants.ctx)
        }
        PI.divide(div).round(MathConstants.ctx)
    }
}
