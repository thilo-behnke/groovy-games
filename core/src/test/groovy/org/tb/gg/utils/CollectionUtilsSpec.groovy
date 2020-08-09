package org.tb.gg.utils

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class CollectionUtilsSpec extends Specification {

    def 'permutations (size = 2): empty collection -> empty collection'() {
        given:
        def list = []
        when:
        def res = CollectionUtils.permutations(list, 2)
        then:
        res == []
    }

    def 'permutations (size = 2): collection with 1 item -> empty collection'() {
        given:
        def list = ['hello']
        when:
        def res = CollectionUtils.permutations(list, 2)
        then:
        res == []
    }

    def 'permutations (size = 2): collection with 2 items -> single permutation in collection'() {
        given:
        def list = ['hello', 'world']
        when:
        def res = CollectionUtils.permutations(list, 2)
        then:
        res == [['hello', 'world']]
    }

    def 'permutations (size = 2): collection with more than 2 items -> single permutation in collection'() {
        given:
        def list = ['hello', 'darkness', 'my', 'old', 'friend']
        when:
        def res = CollectionUtils.permutations(list, 2)
        then:
        res == [['hello', 'darkness'], ['hello', 'my'], ['hello', 'old'], ['hello', 'friend'], ['darkness', 'my'], ['darkness', 'old'], ['darkness', 'friend'], ['my', 'old'], ['my', 'friend'], ['old', 'friend']]
    }
}
