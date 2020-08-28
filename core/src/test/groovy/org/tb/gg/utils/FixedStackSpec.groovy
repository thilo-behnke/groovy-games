package org.tb.gg.utils

import spock.lang.Specification

class FixedStackSpec extends Specification {

    def 'add element to empty stack'() {
        given:
        def objectToAdd = new Object()
        def fixedStack = createStack(5)
        when:
        fixedStack.push(objectToAdd)
        then:
        fixedStack.size == 1
        fixedStack.peek() == objectToAdd
    }

    def 'add element multiple times to empty stack'() {
        given:
        def objectToAdd = new Object()
        def fixedStack = createStack(5)
        when:
        fixedStack.push(objectToAdd)
        fixedStack.push(objectToAdd)
        fixedStack.push(objectToAdd)
        fixedStack.push(objectToAdd)
        then:
        fixedStack.size == 4
        fixedStack.peek() == objectToAdd
    }

    def 'overflow stack -> removes lowest element'() {
        given:
        def objectToAdd = new Object()
        def fixedStack = createStack(5)
        when:
        fixedStack.push(objectToAdd)
        fixedStack.push(objectToAdd)
        fixedStack.push(objectToAdd)
        fixedStack.push(objectToAdd)
        fixedStack.push(objectToAdd)
        fixedStack.push(objectToAdd)
        then:
        fixedStack.size == 5
    }

    private static createStack(int n) {
        FixedStack.ofSize(n)
    }
}
