package global.geom

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class VectorSpec extends Specification {
    def 'vector addition'() {
        expect:
        v1 + v2 == vRes
        where:
        v1 | v2 | vRes
        new Vector(x: 10.2349, y: 49.23049) | new Vector(x: 23.230945, y: 49.29348) | new Vector(x: 33.465845, y: 98.52397)
    }

    def 'vector multiplication'() {
        expect:
        v1 * v2 == vRes
        where:
        v1 | v2 | vRes
        new Vector(x: 234.238, y: 55.433) | new Vector(x: 138.344, y: 696.22) | new Vector(x: 32405.421872, y: 38593.56326)
    }
}