package org.tb.gg.di;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


// Needs to be runtime, because otherwise injection won't work with traits!
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
@GroovyASTTransformationClass("org.tb.gg.di.InjectSingletonASTTransformation")
public @interface Inject {
}