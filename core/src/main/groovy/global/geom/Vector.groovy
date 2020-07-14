package global.geom

class Vector {
    Float x
    Float y

    private final static unit = new Vector(x: 1, y: 1)
    private final static zero = new Vector(x: 0, y: 0)

    Vector plus(Vector b) {
        x = Float.sum(x, b.x)
        y = Float.sum(y, b.y)
        return this
    }

    static unitVector() {
        return unit
    }

    static zeroVector() {
        return zero
    }
}
