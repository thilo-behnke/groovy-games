package di.scanner

import di.Service
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ClassLoaderServiceScannerSpec extends Specification {
    private ClassLoaderServiceScanner serviceScanner
    private ClassLoader classLoaderMock

    void setup() {
//        classLoaderMock = Mock(ClassLoader)
        classLoaderMock = this.getClass().getClassLoader()
        serviceScanner = new ClassLoaderServiceScanner(classLoaderMock)
    }

    def 'should return empty list if no services are found in given class loader'() {
        when:
        def serviceClasses = serviceScanner.scanForServices()
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
