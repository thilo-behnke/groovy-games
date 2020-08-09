package org.tb.gg.utils

class CollectionUtils {

    static List<List> permutations(Set set, size = 2) {
        def list = set.toList()
        permutations(list, size)
    }

    static List<List> permutations(List list, size = 2) {
        if (list.size() <= 1) {
            return []
        }

        list.withIndex().collectMany { def entry, int i ->
            def nextIndex = i + 1
            if (nextIndex >= list.size()) {
                return []
            }
            def rest = list[nextIndex..-1]
            rest.collect{
                [entry, it]
            }
        }
    }

}
