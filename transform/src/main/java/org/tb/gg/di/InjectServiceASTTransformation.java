package org.tb.gg.di;

import groovyjarjarasm.asm.Opcodes;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import static org.codehaus.groovy.ast.ClassHelper.make;


@SuppressWarnings("unused")
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class InjectServiceASTTransformation extends AbstractASTTransformation {
    static final Class<Inject> MY_CLASS = Inject.class;
    static final ClassNode MY_TYPE = make(MY_CLASS);

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode) nodes[1];
        AnnotationNode anno = (AnnotationNode) nodes[0];

        if (!MY_TYPE.equals(anno.getClassNode()) || !(parent instanceof FieldNode)) {
            return;
        }

        FieldNode fieldNode = (FieldNode) parent;
        // TODO: It would be nice to allow implicit injection by leaving out the Class return type, but how to find the matching class for the getter including the package path?
        ClassNode serviceClassNode = fieldNode.getType();
        String serviceName = fieldNode.getName().substring(0, 1).toUpperCase() + fieldNode.getName().substring(1);
        ClassNode clazz = fieldNode.getDeclaringClass();
        // 1. Remove property, no longer needed.
        clazz.removeField(fieldNode.getName());
        ClassNode proxyClassNode = new ClassNode(ServiceProvider.class);
        MethodNode serviceGetter = new MethodNode(
                "get" + serviceName,
                // TODO: Should not always be static - only if necessary.
                Opcodes.ACC_PROTECTED + Opcodes.ACC_STATIC,
                serviceClassNode,
                Parameter.EMPTY_ARRAY,
                ClassNode.EMPTY_ARRAY,
                // 2. Getter returns static proxy access to service, e.g. Proxy.get('<service>')
                new ReturnStatement(
                        new ExpressionStatement(
                                new MethodCallExpression(
                                        new ClassExpression(proxyClassNode),
                                        "getSingletonService",
                                        new ArgumentListExpression(new ConstantExpression(serviceClassNode.getNameWithoutPackage()))
                                )
                        )
                )
        );
        Class<?> annotation = serviceClassNode.isInterface() ? InjectedDynamic.class : Injected.class;
        // 3. Add annotation to later identify the injected getter.
        serviceGetter.addAnnotation(new AnnotationNode(new ClassNode(annotation)));
        // 4. Add getter for service
        clazz.addMethod(serviceGetter);
    }
}
