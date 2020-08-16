package org.tb.gg.gameObject;

import groovy.transform.CompileStatic;
import groovyjarjarasm.asm.Opcodes;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@GroovyASTTransformation()
public class PerishConditionASTTransformation extends AbstractASTTransformation {
    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        AnnotatedNode parent = (AnnotatedNode) nodes[1];
        AnnotationNode anno = (AnnotationNode) nodes[0];

        if (!(parent instanceof ClassNode)) {
            return;
        }

        ClassNode classNode = (ClassNode) parent;
        List<ClassNode> perishableImplementations = Arrays.stream(classNode.getInterfaces())
                .map(impl -> {
                    Set<ClassNode> interfaces = impl.getPlainNodeReference().getAllInterfaces();
                    if (!impl.getPlainNodeReference().equals(new ClassNode(Perishable.class)) && interfaces.contains(new ClassNode(Perishable.class))) {
                        return impl.getPlainNodeReference();
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<BooleanExpression> shouldPerishExpressions = perishableImplementations.stream()
                .map(impl -> classNode.getMethod("shouldPerish__" + impl.getNameWithoutPackage(), Parameter.EMPTY_ARRAY))
                .map(methodNode -> new BooleanExpression(new MethodCallExpression(new VariableExpression("this"), methodNode.getName(), ArgumentListExpression.EMPTY_ARGUMENTS)))
                .collect(Collectors.toList());

        Optional<BooleanExpression> combinedShouldPerishBooleanExpressionOpt = shouldPerishExpressions.stream()
                .reduce((BooleanExpression acc, BooleanExpression booleanExpression) -> new BooleanExpression(new BinaryExpression(acc, Token.newSymbol("||", 0, 0), booleanExpression)));

        if (combinedShouldPerishBooleanExpressionOpt.isEmpty()) {
            return;
        }
        MethodNode shouldPerish = new MethodNode(
                "shouldPerish",
                Opcodes.ACC_PUBLIC,
                new ClassNode(Boolean.class),
                Parameter.EMPTY_ARRAY,
                ClassNode.EMPTY_ARRAY,
                new ReturnStatement(combinedShouldPerishBooleanExpressionOpt.get())
        );

        // Remove existing shouldPerish stub if exists.
        MethodNode existingShouldPerish = classNode.getMethod("shouldPerish", Parameter.EMPTY_ARRAY);
        if (existingShouldPerish != null) {
            classNode.removeMethod(existingShouldPerish);
        }

        classNode.addMethod(shouldPerish);
    }
}
