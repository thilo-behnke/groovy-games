package global.geom

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class VectorOperationSpec extends Specification {
    @Unroll
    def 'vector length: #desc'() {
        expect:
        v.length() == res
        where:
        v                                   || res       || desc
        Vector.unitVector()                 || 1.4142    || "unit vector"
        new Vector(x: 3, y: 4)              || 5         || "no fracture length"
        new Vector(x: 593.11, y: 1903.002)  || 1993.3    || "fracture length"
    }

    @Unroll
    def 'vector normalize: #desc'() {
        expect:
        v.normalize() == vRes
        where:
        v                                   || vRes                                         || desc
        Vector.unitVector()                 || new Vector(x: 0.7071135624, y: 0.7071135624) || "unit vector"
        new Vector(x: 3, y: 4)              || new Vector(x: 0.6, y: 0.8)                   || "no fracture length"
        new Vector(x: 593.11, y: 1903.002)  || new Vector(x: 0.2975517985, y: 0.9546992425) || "fracture length"
    }
}
