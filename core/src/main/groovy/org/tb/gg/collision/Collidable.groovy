package org.tb.gg.collision

interface Collidable<T> {
    boolean collidesWith(T structure)
    T getStructure()
}