package org.tb.gg.collision.handler;

public interface CollisionHandler<S, T> {
    void handleCollision(S a, T b);
    boolean validForTypes(Object a, Object b);
}