package input.keyEvent

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes = ['keyCode'])
class KeyEvent {
   int keyCode
   String keyName
}
