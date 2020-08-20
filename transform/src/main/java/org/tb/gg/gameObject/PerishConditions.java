package org.tb.gg.gameObject;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
@GroovyASTTransformationClass("org.tb.gg.gameObject.PerishConditionASTTransformation")
public @interface PerishConditions {}