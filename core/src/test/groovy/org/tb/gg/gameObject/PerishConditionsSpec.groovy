package org.tb.gg.gameObject

import spock.lang.Specification

class PerishConditionSpec extends Specification {

    ClassWithMultiplePerishConditions gameObject

    def setup() {
        gameObject = new ClassWithMultiplePerishConditions()
    }

    def 'both perish conditions are false -> should not perish'() {
        expect:
        !gameObject.shouldPerish()
    }

    def 'perish condition 1 is true -> should perish'() {
        when:
        gameObject.perishConditionProp1 = true
        then:
        gameObject.shouldPerish()
    }

    def 'perish condition 2 is true -> should perish'() {
        when:
        gameObject.perishConditionProp2 = true
        then:
        gameObject.shouldPerish()
    }

    def 'perish condition 1 and 2 are true -> should perish'() {
        when:
        gameObject.perishConditionProp2 = true
        then:
        gameObject.shouldPerish()
    }
}


class ClassWithMultiplePerishConditions extends BaseGameObject implements PerishCondition1, PerishCondition2 {}

@PerishCondition
trait PerishCondition1 implements Perishable {
    def perishConditionProp1 = false

    Boolean shouldPerish__PerishCondition1() {
        return perishConditionProp1
    }
}

@PerishCondition
trait PerishCondition2 implements Perishable {
    def perishConditionProp2 = false

    Boolean shouldPerish__PerishCondition2() {
        return perishConditionProp2
    }
}
