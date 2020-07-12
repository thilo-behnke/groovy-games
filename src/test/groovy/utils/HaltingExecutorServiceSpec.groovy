package utils

import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@Unroll
class HaltingExecutorServiceSpec extends Specification {
    HaltingExecutorService haltingExecutorService

    def setup() {
        haltingExecutorService = new HaltingExecutorService()
    }

    def 'should execute a single task asynchronously'() {
        when:
        def future = haltingExecutorService.submit(() -> 'hello')
        then:
        future.get()
        future.isDone()
    }

    def 'should execute multiple tasks asynchronously, in the order they were added'() {
        when:
        def future = haltingExecutorService.submit(() -> System.println('hello'))
        def future2 = haltingExecutorService.submit(() -> System.println('world'))
        then:
        future.get(100, TimeUnit.MILLISECONDS)
        future.isDone()
        future2.isDone()
    }

    def 'should stop executing tasks when set to halt'() {
        when:
        def (future, future2) = prepareHaltedExecution()
        then:
        future.isDone()
        !future2.isDone()
    }

    def 'should resume executing tasks when continueExecution is triggered'() {
        when:
        def (future, future2) = prepareHaltedExecution()
        haltingExecutorService.continueExecution()
        then:
        future.isDone()
        future2.isDone()
    }

    private prepareHaltedExecution() {
        def future = haltingExecutorService.submit(() -> 'hello').thenComposeAsync({ haltingExecutorService.halt() })
        def future2 = haltingExecutorService.submit(() -> 'world')
        return [future, future2]
    }
}
