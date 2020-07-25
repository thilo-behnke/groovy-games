package di.scanner

import di.Service
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class DefaultServiceScannerSpec extends Specification {
    private DefaultServiceScanner serviceScanner

    void setup() {
        serviceScanner = new DefaultServiceScanner()
    }

    def 'should return empty list if no services are found in given package'() {
        given:
        def pkg = 'org.does.not.exist'
        when:
        def serviceClasses = serviceScanner.scanForServices(pkg)
        then:
        serviceClasses == (Set<Class<Service>>) []
    }

    def 'should find the single service class in the given package'() {
        given:
        def pkg = 'org.does.not.exist'
        when:
        def serviceClasses = serviceScanner.scanForServices(pkg)
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
