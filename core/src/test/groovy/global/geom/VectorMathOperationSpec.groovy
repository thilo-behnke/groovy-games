package global.geom

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class VectorMathOperationSpec extends Specification {
    def 'vector addition'() {
        expect:
        v1 + v2 == vRes
        where:
        v1                                  | v2                                    | vRes
        new Vector(x: 10.2349, y: 49.23049) | new Vector(x: 23.230945, y: 49.29348) | new Vector(x: 33.465845, y: 98.52397)
    }

    def 'vector scalar addition'() {
        expect:
        v1 + s == vRes
        where:
        v1                                      | s       | vRes
        new Vector(x: 595.304, y: 29830.219002) | 59.2939 | new Vector(x: 654.5979, y: 29889.512902)
    }

    def 'vector subtraction'() {
        expect:
        v1 + v2 == vRes
        where:
        v1                                  | v2                                    | vRes
        new Vector(x: 96.29032, y: 233.203) | new Vector(x: 45.5095, y: 994.223222) | new Vector(x: 141.79982, y: 1227.426222)
    }

    def 'vector scalar subtraction'() {
        expect:
        v1 - s == vRes
        where:
        v1                                  | s       | vRes
        new Vector(x: 111.19, y: 5193.3939) | 83.9298 | new Vector(x: 27.2602, y: 5109.4641)
    }

    def 'vector multiplication'() {
        expect:
        v1 * v2 == vRes
        where:
        v1                                | v2                                | vRes
        new Vector(x: 234.238, y: 55.433) | new Vector(x: 138.344, y: 696.22) | new Vector(x: 32405.421872, y: 38593.56326)
    }

    def 'vector scalar multiplication'() {
        expect:
        v1 * s == vRes
        where:
        v1                                | s       | vRes
        new Vector(x: 2304.39, y: 88.203) | 4.22299 | new Vector(x: 9731.4159261, y: 372.48038697)
    }
}
