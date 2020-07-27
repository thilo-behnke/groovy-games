package org.tb.gg.config

import org.tb.gg.global.geom.Vector

class ConfigurationSettings {
    Tuple2<Integer, Integer> resolution
    WindowMode windowMode

    enum WindowMode {
        WINDOWED, FULLSCREEN
    }
}
