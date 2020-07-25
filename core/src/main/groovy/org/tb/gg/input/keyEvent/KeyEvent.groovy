package org.tb.gg.input.keyEvent

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['keyCode'])
class KeyEvent {
   int keyCode
   String keyName
}
