package org.tb.gg.env.systemProperty

interface SystemPropertyProvider {
    Optional<String> getProperty(String name)
}