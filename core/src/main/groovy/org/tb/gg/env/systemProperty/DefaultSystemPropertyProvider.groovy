package org.tb.gg.env.systemProperty

class DefaultSystemPropertyProvider implements SystemPropertyProvider {
    @Override
    Optional<String> getProperty(String propertyName) {
        Optional.ofNullable(System.getProperty(propertyName))
    }
}
