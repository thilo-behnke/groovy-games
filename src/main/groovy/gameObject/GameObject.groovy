package gameObject

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes='id')
abstract class GameObject {
    Long id

    abstract update(Long timestamp, Long delta)
}
