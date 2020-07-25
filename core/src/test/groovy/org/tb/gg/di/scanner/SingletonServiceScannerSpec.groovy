package org.tb.gg.di.scanner

import org.tb.gg.di.Service
import org.tb.gg.di.provider.ClassDefinition
import org.tb.gg.di.provider.ClassDefinitionProvider
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class SingletonServiceScannerSpec extends Specification {
    private SingletonServiceScanner serviceScanner

    void setup() {
        serviceScanner = new SingletonServiceScanner()
    }

    def 'should return empty list if no singleton services are found in the given class defs'() {
        given:
        def clazzDefs = (Set<ClassDefinition>) []
        when:
        def serviceClasses = serviceScanner.scanForServices(clazzDefs)
        then:
        serviceClasses == (Set<Class<Service>>) []
    }

    def 'should find the single singleton service within the class defs'() {
        given:
        def clazzDefs = (Set<ClassDefinition>) [new ClassDefinition(className: "org.tb.gg.someClass", packageName: "org.tb.gg")]
        when:
        def serviceClasses = serviceScanner.scanForServices(clazzDefs)
        then:
        serviceClasses == (Set<Class<Service>>) []
    }

    def 'should find the single service class in the complete app when no pkg is provided'() {
        given:
        def pkg = 'org.does.not.exist'
        when:
        def serviceClasses = serviceScanner.scanForServices(pkg)
        then:
        serviceClasses == (Set<Class<Service>>) []
    }
}
