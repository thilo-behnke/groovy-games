package org.tb.gg.renderer.options

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class RenderOptions {
    DrawColor drawColor
    static final empty = new RenderOptions()
}
