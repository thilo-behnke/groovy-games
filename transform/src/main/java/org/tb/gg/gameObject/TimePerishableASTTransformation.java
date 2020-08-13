package org.tb.gg.gameObject;

import groovyjarjarasm.asm.Opcodes;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@SuppressWarnings("unused")
@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
public class TimePerishableASTTransformation extends AbstractASTTransformation {
    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        AnnotatedNode parent = (AnnotatedNode) nodes[1];
        AnnotationNode anno = (AnnotationNode) nodes[0];

        if (!(parent instanceof ClassNode)) {
            return;
        }
        ClassNode classNode = (ClassNode) parent;

        FieldNode dateProvider = classNode.getField("dateProvider");

        FieldNode spawnedAt = new FieldNode("spawnedAt", Opcodes.ACC_PRIVATE, new ClassNode(Long.class), classNode, null);
        classNode.addField(spawnedAt);

        FieldNode ttl = new FieldNode("TTL_MS", Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL, new ClassNode(Long.class), classNode, anno.getMember("value"));
        classNode.addField(ttl);

        classNode.addConstructor(
                new ConstructorNode(Opcodes.ACC_PUBLIC, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, new ExpressionStatement(
                        new BinaryExpression(
                                new FieldExpression(
                                        spawnedAt
                                ),
                                Token.newSymbol("=", 0, 0),
                                new MethodCallExpression(new FieldExpression(dateProvider), "now", ArgumentListExpression.EMPTY_ARGUMENTS)
                        )
                ))
        );

        BinaryExpression spawnedToTimestampDiff = new BinaryExpression(
                new VariableExpression("timestamp"),
                Token.newSymbol("-", 0, 0),
                new FieldExpression(spawnedAt)
        );
        BinaryExpression diffLargerThanTTL = new BinaryExpression(
                spawnedToTimestampDiff,
                Token.newSymbol(">", 0, 0),
                new FieldExpression(ttl)
        );

        MethodNode shouldPerish = new MethodNode(
                "shouldPerish",
                Opcodes.ACC_PUBLIC,
                new ClassNode(boolean.class),
                new Parameter[]{
                        new Parameter(new ClassNode(Long.class), "timestamp"),
                        new Parameter(new ClassNode(Long.class), "delta")
                },
                ClassNode.EMPTY_ARRAY,
                new ReturnStatement(new ExpressionStatement(diffLargerThanTTL))
        );

    }
}
