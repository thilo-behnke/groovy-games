package org.tb.gg.gameObject;

import groovyjarjarasm.asm.Opcodes;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@SuppressWarnings("unused")
@GroovyASTTransformation()
public class PerishWhenOutOfBoundsASTTransformation extends AbstractASTTransformation {
    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        AnnotatedNode parent = (AnnotatedNode) nodes[1];
        AnnotationNode anno = (AnnotationNode) nodes[0];

        if (!(parent instanceof ClassNode)) {
            return;
        }
        ClassNode classNode = (ClassNode) parent;

        MethodCallExpression checkIfOutOfBounds = new MethodCallExpression (
                new VariableExpression("this"),
                "isOutOfBounds",
                ArgumentListExpression.EMPTY_ARGUMENTS
        );

        MethodNode shouldPerishWhenOutOfBounds = new MethodNode(
                "shouldPerish__OutOfBoundsPerishable",
                Opcodes.ACC_PRIVATE,
                new ClassNode(Boolean.class),
                Parameter.EMPTY_ARRAY,
                ClassNode.EMPTY_ARRAY,
                new ReturnStatement(new BooleanExpression(checkIfOutOfBounds))
        );

        classNode.addMethod(shouldPerishWhenOutOfBounds);
    }
}
