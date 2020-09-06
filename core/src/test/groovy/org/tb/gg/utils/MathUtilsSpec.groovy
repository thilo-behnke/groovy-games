package org.tb.gg.utils

import spock.lang.Specification

class MathUtilsSpec extends Specification {

    def 'normalizeInRange: throw exception when from is higher than low for n = #n, from = #from, to = #to'() {
        when:
        MathUtils.normalizeInRange(n, from, to)
        then:
        thrown(IllegalArgumentException)
        where:
        n   | from  | to
        4.0 | 11.0  | 10.0
        4.0 | 11.0  | -10.0
        4.0 | -11.0 | -13.0
    }

    def 'normalizeInRange: valid parameters'() {
        when:
        def res = MathUtils.normalizeInRange(n, from, to)
        then:
        res == expectedRes
        where:
        n     | from  | to   | expectedRes
        4.0   | 11.0  | 11.0 | 11.0
        4.0   | 11.0  | 15.0 | 11.0
        84.0  | 11.0  | 15.0 | 15.0
        13.0  | 11.0  | 15.0 | 13.0
        -13.0 | 11.0  | 15.0 | 11.0
        -13.0 | -14.0 | -1.0 | -13.0
    }
}
