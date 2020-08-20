package org.tb.gg.di;

import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;

@SuppressWarnings("unused")
abstract class AbstractInjectASTTransformation extends AbstractASTTransformation {
    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode) nodes[1];
        AnnotationNode anno = (AnnotationNode) nodes[0];

        if (!getType().equals(anno.getClassNode()) || !(parent instanceof FieldNode)) {
            return;
        }
        validateTransformation(nodes, source);

        FieldNode fieldNode = (FieldNode) parent;
        // TODO: It would be nice to allow implicit injection by leaving out the Class return type, but how to find the matching class for the getter including the package path?
        ClassNode serviceClassNode = fieldNode.getType();
        String serviceName = fieldNode.getName().substring(0, 1).toUpperCase() + fieldNode.getName().substring(1);
        ClassNode clazz = fieldNode.getDeclaringClass();
        // 1. Remove property, no longer needed.
        clazz.removeField(fieldNode.getName());

        MethodNode serviceGetter = getterImplementation(serviceName, serviceClassNode);

        Class<?> annotation = serviceClassNode.isInterface() ? InjectedDynamic.class : Injected.class;
        // 3. Add annotation to later identify the injected getter.
        serviceGetter.addAnnotation(new AnnotationNode(new ClassNode(annotation)));
        // 4. Add getter for service
        clazz.addMethod(serviceGetter);
    }

    abstract void validateTransformation(ASTNode[] nodes, SourceUnit source) throws IllegalArgumentException;
    abstract ClassNode getType();
    abstract MethodNode getterImplementation(String serviceName, ClassNode serviceClassNode);
}
