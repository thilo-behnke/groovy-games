package org.tb.gg.utils

import java.util.stream.Collectors

class FixedStack<T> implements Stack<T> {
    private int maxSize
    private java.util.Stack<T> stack

    private FixedStack(int size) {
        super()
        maxSize = size >= 0 ? size : 0
        stack = new java.util.Stack<T>()
    }

    static ofSize(int n) {
        def fixedStack = new FixedStack(n)
        fixedStack
    }

    @Override
    int getSize() {
        return stack.size()
    }

    @Override
    T push(T item) {
        if (this.size >= maxSize) {
            def elements = stack.stream().skip(1).collect(Collectors.toList())
            stack.clear()
            for (T element : elements) {
                stack.push(element)
            }
        }
        return stack.push(item)
    }

    @Override
    T pop() {
        return (T) stack.pop()
    }

    @Override
    T peek() {
        if (stack.size() == 0) {
            return null
        }
        return stack.peek()
    }

    @Override
    void empty() {
        stack.empty()
    }
}
