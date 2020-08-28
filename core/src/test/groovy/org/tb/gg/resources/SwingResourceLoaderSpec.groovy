package org.tb.gg.resources

import spock.lang.Specification

class SwingResourceLoaderSpec extends Specification {

    private SwingResourceLoader swingResourceLoader

    def setup() {
        swingResourceLoader = new SwingResourceLoader()
    }

    def 'should find loaded resource file'() {
        given:
        def resourcePath = 'images/missile-01.png'
        def resourceName = 'MISSILE'
        when:
        swingResourceLoader.loadResource(resourcePath, resourceName)
        then:
        swingResourceLoader.getResource(resourceName).isPresent()
    }

    def 'should not find resource file that was not loaded'() {
        def resourceName = 'MISSILE'
        expect:
        swingResourceLoader.getResource(resourceName).isEmpty()
    }

}
