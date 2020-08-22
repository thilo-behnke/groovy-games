package gep;

import groovyjarjarasm.asm.Opcodes;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.*;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.syntax.Types;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.tb.gg.collision.handler.CollisionHandler;

import java.util.List;
import java.util.stream.Collectors;

@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class GlobalCollisionHandlerASTTransformation extends AbstractASTTransformation {

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        List<ClassNode> collisionHandlers = findCollisionHandlers(source);
        if (collisionHandlers.isEmpty()) {
            return;
        }

        collisionHandlers.forEach(collisionHandler -> {
            addGenericParametersToClass(collisionHandler);
            addInverseImplementationCheckToHandleCollision(collisionHandler);
        });

    }

    private void addGenericParametersToClass(ClassNode collisionHandler) {
        GenericsType[] genericsTypes = collisionHandler.getSuperClass().getGenericsTypes();
        if (genericsTypes.length < 2) {
            throw new IllegalStateException("CollisionHandler must have 2 generic types!");
        }
        Parameter[] handleCollisionParameters = collisionHandler.getMethods("handleCollision").get(0).getParameters();
        ClassNode objectA = handleCollisionParameters[0].getType();
        ClassNode objectB = handleCollisionParameters[1].getType();

        collisionHandler.addField(
                new FieldNode("objectTypeA", Opcodes.ACC_PUBLIC, new ClassNode(Class.class), collisionHandler, new ClassExpression(objectA))
        );

        collisionHandler.addField(
                new FieldNode("objectTypeB", Opcodes.ACC_PUBLIC, new ClassNode(Class.class), collisionHandler, new ClassExpression(objectB))
        );
    }

    private void addInverseImplementationCheckToHandleCollision(ClassNode collisionHandler) {
        MethodNode handleCollision = collisionHandler.getMethods("handleCollision").get(0);
        MethodNode handleCollisionImplementation = new MethodNode("handleCollisionImplementation", handleCollision.getModifiers(), handleCollision.getReturnType(), handleCollision.getParameters(), handleCollision.getExceptions(), handleCollision.getCode());

        collisionHandler.removeMethod(handleCollision);

        Statement inverseImplementationCheck = createInverseImplementationCheck(collisionHandler);
        MethodNode handleCollisionInverseCheck = new MethodNode("handleCollision", handleCollision.getModifiers(), handleCollision.getReturnType(), handleCollision.getParameters(), handleCollision.getExceptions(), inverseImplementationCheck);
        collisionHandler.addMethod(handleCollisionInverseCheck);

        collisionHandler.addMethod(handleCollisionImplementation);
    }

    private Statement createInverseImplementationCheck(ClassNode collisionHandler) {
//        BooleanExpression defaultCase = new BooleanExpression(new BinaryExpression());
        FieldNode objectTypeA = collisionHandler.getField("objectTypeA");
        FieldNode objectTypeB = collisionHandler.getField("objectTypeB");
        VariableExpression objectX = new VariableExpression("a");
        VariableExpression objectY = new VariableExpression("b");
        // TODO: How to get instanceof to work? Would be better than class.equals(class).
        BooleanExpression defaultTypeCheck = new BooleanExpression(
                new BinaryExpression(
                        new MethodCallExpression(objectX, "equals", new ArgumentListExpression(new FieldExpression[]{new FieldExpression(objectTypeA)})),
                        Token.newSymbol("&&", 0, 0),
                        new MethodCallExpression(objectY, "equals", new ArgumentListExpression(new FieldExpression[]{new FieldExpression(objectTypeB)}))
                )
        );
        BooleanExpression invertedTypeCheck = new BooleanExpression(
                new BinaryExpression(
                        new MethodCallExpression(objectY, "equals", new ArgumentListExpression(new FieldExpression[]{new FieldExpression(objectTypeA)})),
                        Token.newSymbol("&&", 0, 0),
                        new MethodCallExpression(objectX, "equals", new ArgumentListExpression(new FieldExpression[]{new FieldExpression(objectTypeB)}))
                )
        );
        MethodCallExpression regularHandleCollision = new MethodCallExpression(new VariableExpression("this"), "handleCollisionImplementation", new ArgumentListExpression(new VariableExpression[]{objectX, objectY}));
        MethodCallExpression invertedHandleCollision = new MethodCallExpression(new VariableExpression("this"), "handleCollisionImplementation", new ArgumentListExpression(new VariableExpression[]{objectY, objectX}));
        IfStatement defaultTypeCheckIfStmt = new IfStatement(
                defaultTypeCheck,
                new ExpressionStatement(regularHandleCollision),
                new IfStatement(
                        invertedTypeCheck,
                        new ExpressionStatement(invertedHandleCollision),
                        // Create fall through if the collision handler doesn't match the provided objects.
                        new ExpressionStatement(new EmptyExpression())
                )
        );
        return defaultTypeCheckIfStmt;
    }

    private List<ClassNode> findCollisionHandlers(SourceUnit source) {
        return source.getAST().getClasses().stream()
                .filter(this::isCollisionHandler)
                .collect(Collectors.toList());
    }

    private boolean isCollisionHandler(ClassNode classNode) {
        if (classNode.isAbstract() || classNode.isInterface()) {
            return false;
        }
        return classNode.getSuperClass().getAllInterfaces().stream().anyMatch(it -> it.equals(new ClassNode(CollisionHandler.class)));
    }

}
