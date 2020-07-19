package global.math

import java.math.MathContext
import java.math.RoundingMode

class MathConstants {
    final static ctx = new MathContext(5, RoundingMode.HALF_UP)
    final static PI = BigDecimal.valueOf(Math.PI)
}
