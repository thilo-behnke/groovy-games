package org.tb.gg.gameObject;

import groovyjarjarasm.asm.Opcodes;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@GroovyASTTransformation
public class PerishConditionASTTransformation extends AbstractASTTransformation {
    private ClassNode classNode;
    private MethodNode existingShouldPerish;
    private BooleanExpression perishableImplementationBooleanExpression;

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        boolean isValidTransformation = verifyTransformation(nodes);
        if (!isValidTransformation) {
            return;
        }
        extractShouldPerishImplementations();
        createShouldPerishMethod();
    }

    private boolean verifyTransformation(ASTNode[] nodes) {
        AnnotatedNode parent = (AnnotatedNode) nodes[1];
        AnnotationNode anno = (AnnotationNode) nodes[0];

        if (!(parent instanceof ClassNode)) {
            return false;
        }

        classNode = (ClassNode) parent;
        return true;
    }

    private void extractShouldPerishImplementations() {
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

        perishableImplementationBooleanExpression = combinedShouldPerishBooleanExpressionOpt.orElse(null);
    }

    private void createShouldPerishMethod() {
        existingShouldPerish = classNode.getMethod("shouldPerish", Parameter.EMPTY_ARRAY);

        if (existingShouldPerish != null) {
            classNode.removeMethod(existingShouldPerish);
        }

        ReturnStatement shouldPerishReturnStatement = createShouldPerishReturnStatement();

        MethodNode shouldPerish = new MethodNode(
                "shouldPerish",
                Opcodes.ACC_PUBLIC,
                new ClassNode(Boolean.class),
                Parameter.EMPTY_ARRAY,
                ClassNode.EMPTY_ARRAY,
                shouldPerishReturnStatement
        );

        classNode.addMethod(shouldPerish);
    }

    private ReturnStatement createShouldPerishReturnStatement() {
        // TODO: Currently it is not possible to have the shouldPerish implementation in the class itself, only in a super class works. Improve.
        if (existingShouldPerish == null || existingShouldPerish.getDeclaringClass().equals(classNode)) {
            return new ReturnStatement(perishableImplementationBooleanExpression);
        }
        // If provided by a super class, also add the shouldPerish implementation to the boolean expression.
        BooleanExpression booleanExpression = new BooleanExpression(
                new BinaryExpression(
                        new MethodCallExpression(new VariableExpression("super"), "shouldPerish", ArgumentListExpression.EMPTY_ARGUMENTS),
                        Token.newSymbol("||", 0, 0),
                        perishableImplementationBooleanExpression
                )
        );
        return new ReturnStatement(booleanExpression);
    }
}
