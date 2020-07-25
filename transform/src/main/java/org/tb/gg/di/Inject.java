package org.tb.gg.di;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@GroovyASTTransformationClass("org.tb.gg.di.InjectServiceASTTransformation")
public @interface Inject {
}