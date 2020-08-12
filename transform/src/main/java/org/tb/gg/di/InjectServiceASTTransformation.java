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

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        AnnotationNode annotationNode = (AnnotationNode) nodes[0];
        if (!MY_TYPE.equals(annotationNode.getClassNode())) {
            return;
        }

        if (parent instanceof FieldNode) {
            FieldNode fieldNode = (FieldNode) parent;
            ClassNode serviceClassNode = fieldNode.getType();
            String serviceName = fieldNode.getName().substring(0, 1).toUpperCase() + fieldNode.getName().substring(1);
            ClassNode clazz = fieldNode.getDeclaringClass();
            // 1. Remove property, no longer needed.
            clazz.removeField(fieldNode.getName());
            ClassNode proxyClassNode = new ClassNode(ServiceProvider.class);
            MethodNode serviceGetter = new MethodNode(
                    "get" + serviceName,
                    // TODO: field.getModifiers would be better, but returns private - what is the problem?
                    Opcodes.ACC_PROTECTED + Opcodes.ACC_STATIC,
                    serviceClassNode,
                    Parameter.EMPTY_ARRAY,
                    ClassNode.EMPTY_ARRAY,
                    // 2. Getter returns static proxy access to service, e.g. Proxy.get('<service>')
                    new ReturnStatement(
                            new ExpressionStatement(
                                    new MethodCallExpression(
                                            new ClassExpression(proxyClassNode),
                                            "getService",
                                            new ArgumentListExpression(new ConstantExpression(serviceClassNode.getNameWithoutPackage()))
                                    )
                            )
                    )
            );
            serviceGetter.addAnnotations(getOtherAnnotationsOfNode(fieldNode));
            Class<?> annotation = serviceClassNode.isInterface() ? InjectedDynamic.class : Injected.class;
            // 3. Add annotation to later identify the injected getter.
            serviceGetter.addAnnotation(new AnnotationNode(new ClassNode(annotation)));
            // 4. Add getter for service
            clazz.addMethod(serviceGetter);
        }
    }

    private List<AnnotationNode> getOtherAnnotationsOfNode(FieldNode node) {
        return node.getAnnotations().stream()
                .filter(annotationNode -> {
                    // TODO: No better way to compare classes than by name?
                    return !annotationNode.getClassNode().getTypeClass().getName().equals(Inject.class.getName());
                })
                .collect(Collectors.toList());
    }
}
