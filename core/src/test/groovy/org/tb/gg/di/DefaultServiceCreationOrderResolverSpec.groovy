package org.tb.gg.di

import org.tb.gg.di.definition.Service
import spock.lang.Specification;
import spock.lang.Unroll;

@Unroll
public class DefaultServiceCreationOrderResolverSpec extends Specification {
    SinglePipelineServiceCreationOrderResolver singlePipelineServiceCreationOrderResolver

    class WithoutInjectedProps implements Service {
        private myProp
        boolean myOtherProp

        @Override
        void init() {

        }

        @Override
        void destroy() {

        }
    }

    class ClassWithInjectedProp1 implements Service {
        private boolean myBoolean

        @InjectedStatic
        private WithoutInjectedProps getWithoutInjectedProps() {
            return new WithoutInjectedProps()
        }

        @Override
        void init() {

        }

        @Override
        void destroy() {

        }
    }

    class ClassWithInjectedProp2 implements Service {
        private boolean myBoolean

        @InjectedStatic
        private ClassWithInjectedProp1 getWithInjectedProp1() {
            return new ClassWithInjectedProp1()
        }

        @Override
        void init() {

        }

        @Override
        void destroy() {

        }
    }

    void setup() {
        singlePipelineServiceCreationOrderResolver = new SinglePipelineServiceCreationOrderResolver()
    }

    def 'should return an empty array of classes for an empty input array'() {
        given:
        def classes = (Set<Class<Service>>) []
        when:
        def orderedClasses = singlePipelineServiceCreationOrderResolver.determineCreationOrder(classes)
        then:
        orderedClasses == [[]]
    }


    def 'should return a single class in the output array if given a single class without injected props'() {
        given:
        def classes = (Set<Class<Service>>) [WithoutInjectedProps]
        when:
        def orderedClasses = singlePipelineServiceCreationOrderResolver.determineCreationOrder(classes)
        then:
        orderedClasses == [[WithoutInjectedProps]]
    }

    def 'should determine the creation order correctly for one class with an injected service'() {
        given:
        def classes = (Set<Class<Service>>) [WithoutInjectedProps, ClassWithInjectedProp1]
        when:
        def orderedClasses = singlePipelineServiceCreationOrderResolver.determineCreationOrder(classes)
        then:
        orderedClasses == [[WithoutInjectedProps, ClassWithInjectedProp1]]
    }

    def 'should throw an exception if an injected service is not a member of the provided service classes'() {
        given:
        def classes = (Set<Class<Service>>) [ClassWithInjectedProp1]
        when:
        singlePipelineServiceCreationOrderResolver.determineCreationOrder(classes)
        then:
        thrown IllegalStateException
    }

    def 'should correctly order a set of two services, where one depends on the other'() {
        given:
        def classes = (Set<Class<Service>>) [ClassWithInjectedProp2, ClassWithInjectedProp1, WithoutInjectedProps]
        when:
        def orderedClasses = singlePipelineServiceCreationOrderResolver.determineCreationOrder(classes)
        then:
        orderedClasses == [[WithoutInjectedProps, ClassWithInjectedProp1, ClassWithInjectedProp2]]
    }

}
