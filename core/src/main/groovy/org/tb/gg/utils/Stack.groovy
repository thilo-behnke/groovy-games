package org.tb.gg.utils

interface Stack<T> {
    T push(T item)
    T pop()
    T peek()
    int getSize()
    void empty()
}
