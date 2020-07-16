package global.geom

class Vector {
    Float x
    Float y

    private final static unit = new Vector(x: 1, y: 1)
    private final static zero = new Vector(x: 0, y: 0)

    Vector plus(Vector b) {
        def x = Float.sum(x, b.x)
        def y = Float.sum(y, b.y)
        return new Vector(x: x, y: y)
    }

    Vector plus(Long n) {
        def x = (Float) (x + n)
        def y = (Float) (y + n)
        return new Vector(x: x, y: y)
    }

    Vector minus(Vector b) {
        def x = (Float) (x - b.x)
        def y = (Float) (y - b.y)
        return new Vector(x: x, y: y)
    }

    Vector multiply(Float n) {
        def x = (Float) (x * n)
        def y = (Float) (y * n)
        return new Vector(x: x, y: y)
    }

    Float length() {
        return Math.sqrt(x ** 2 + y ** 2)
    }

    static unitVector() {
        return unit
    }

    static zeroVector() {
        return zero
    }
}
