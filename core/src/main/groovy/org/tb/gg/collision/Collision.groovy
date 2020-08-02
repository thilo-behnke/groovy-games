package org.tb.gg.collision

import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable
import groovy.transform.ImmutableOptions
import groovy.transform.ToString
import org.tb.gg.gameObject.GameObject

@EqualsAndHashCode
@ToString
class Collision {
    GameObject a
    GameObject b
}
