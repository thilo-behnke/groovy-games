package global.geom

// TODO: Why can't this be a trait?
abstract class Vector<T> {
    T x
    T y
}

class FVector extends Vector<Float> {
    Float x
    Float y
}
