package org.tb.gg.di;

import groovyjarjarasm.asm.Opcodes;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.util.Arrays;
import java.util.List;

import static org.codehaus.groovy.ast.ClassHelper.make;

@SuppressWarnings("unused")
@GroovyASTTransformation()
public class InjectMultiInstanceASTTransformation extends AbstractInjectASTTransformation {
    static final Class<MultiInject> MY_CLASS = MultiInject.class;
    static final ClassNode MY_TYPE = make(MY_CLASS);

    @Override
    ClassNode getType() {
        return MY_TYPE;
    }

    @Override
    MethodNode getterImplementation(String serviceName, ClassNode serviceClassNode) {
        ClassNode proxyClassNode = new ClassNode(ServiceProvider.class);
        String genericListTypeName = serviceClassNode.getGenericsTypes()[0].getType().getNameWithoutPackage();
        return new MethodNode(
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
                                        "getMultiInstanceServices",
                                        new ArgumentListExpression(new ConstantExpression(genericListTypeName))
                                )
                        )
                )
        );
    }

    @Override
    void validateTransformation(ASTNode[] nodes, SourceUnit source) throws IllegalArgumentException {
        FieldNode parent = (FieldNode) nodes[1];
        if (parent.getType().implementsInterface(new ClassNode(List.class))) {
            throw new IllegalArgumentException("Invalid use of MultiInject annotation. Only for valid for List<T> types. Provided here for field " + parent + " with type " + parent.getType());
        }
    }
}
