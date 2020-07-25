package org.tb.gg.input.awt


import com.google.common.collect.HashBiMap
import groovy.transform.Memoized
import org.tb.gg.input.Key

import java.awt.event.KeyEvent

class AwtKeyCodeConverter {
    private static Map<Key, Integer> keyMapping = HashBiMap.create((Map<Key, Integer>) [
            // Characters.
            (Key.W)    : KeyEvent.VK_W,
            (Key.A)    : KeyEvent.VK_A,
            (Key.S)    : KeyEvent.VK_S,
            (Key.D)    : KeyEvent.VK_D,
            // Numbers.
            (Key.ZERO) : KeyEvent.VK_0,
            (Key.ONE)  : KeyEvent.VK_1,
            (Key.TWO)  : KeyEvent.VK_2,
            (Key.THREE): KeyEvent.VK_3,
            (Key.FOUR) : KeyEvent.VK_4,
            (Key.FIVE) : KeyEvent.VK_5,
            (Key.SIX)  : KeyEvent.VK_6,
            (Key.SEVEN): KeyEvent.VK_7,
            (Key.EIGHT): KeyEvent.VK_8,
            (Key.NINE) : KeyEvent.VK_9,
            // Control keys.
            (Key.UP)   : KeyEvent.VK_UP,
            (Key.DOWN) : KeyEvent.VK_DOWN,
            (Key.LEFT) : KeyEvent.VK_LEFT,
            (Key.RIGHT): KeyEvent.VK_RIGHT,
            // Special keys.
            (Key.ESC)  : KeyEvent.VK_ESCAPE,
            (Key.CTRL) : KeyEvent.VK_CONTROL,
            (Key.SHIFT): KeyEvent.VK_SHIFT,
            (Key.SPACE): KeyEvent.VK_SPACE,
            (Key.TAB)  : KeyEvent.VK_TAB
    ])

    static List<Integer> convertKeysToAwtKeyCodes(Key... keys) {
        keys.collect { convert(it) }
    }

    @Memoized
    private static Integer convert(Key key) {
        return keyMapping.get(key)
    }

    static List<Key> convertAwtKeyCodesToKeys(Integer... keyCodes) {
        keyCodes.collect { convert(it) }
    }

    @Memoized
    private static Key convert(Integer keyCode) {
        keyMapping.inverse().get(keyCode)
    }
}
