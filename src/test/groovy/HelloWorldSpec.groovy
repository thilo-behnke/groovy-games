import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class HelloWorldSpec extends Specification {

    def 'correct'() {
        expect:
        2 == 2
    }

    def 'false'() {
        expect:
        2 == 4
    }

}
