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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class GlobalCollisionHandlerASTTransformation extends AbstractASTTransformation {

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        List<ClassNode> collisionHandlers = findCollisionHandlers(source);
        if (collisionHandlers.isEmpty()) {
            return;
        }
        System.out.println(collisionHandlers);

        collisionHandlers.forEach(collisionHandler -> {
            addGenericParametersToClass(collisionHandler);
            System.out.println("test1");
            addInverseImplementationCheckToHandleCollision(collisionHandler);
            System.out.println("test2");
        });

    }

    private void addGenericParametersToClass(ClassNode collisionHandler) {
        GenericsType[] genericsTypes = collisionHandler.getAllInterfaces().stream().filter(it -> it.implementsInterface(new ClassNode(CollisionHandler.class))).findFirst().get().getGenericsTypes();
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
        // TODO: How to get the lowest bound of the generic parameter?
        Parameter[] handleCollisionParameters = {new Parameter(new ClassNode(Object.class), "a"), new Parameter(new ClassNode(Object.class), "b")};

        MethodNode handleCollision = collisionHandler.getMethods("handleCollision").get(0);
        MethodNode handleCollisionImplementation = new MethodNode("handleCollisionImplementation", handleCollision.getModifiers(), handleCollision.getReturnType(), handleCollision.getParameters(), handleCollision.getExceptions(), handleCollision.getCode());

        collisionHandler.removeMethod(handleCollision);

        Statement inverseImplementationCheck = createInverseImplementationCheck(collisionHandler);
        MethodNode handleCollisionInverseCheck = new MethodNode("handleCollision", handleCollision.getModifiers(), handleCollision.getReturnType(), handleCollisionParameters, handleCollision.getExceptions(), inverseImplementationCheck);
        collisionHandler.addMethod(handleCollisionInverseCheck);

        collisionHandler.addMethod(handleCollisionImplementation);
    }

    private Statement createInverseImplementationCheck(ClassNode collisionHandler) {
//        BooleanExpression defaultCase = new BooleanExpression(new BinaryExpression());
        FieldNode objectTypeA = collisionHandler.getField("objectTypeA");
        FieldNode objectTypeB = collisionHandler.getField("objectTypeB");
        VariableExpression objectX = new VariableExpression("a");
        VariableExpression objectY = new VariableExpression("b");
        BooleanExpression defaultTypeCheck = new BooleanExpression(
                new BinaryExpression(
                        new MethodCallExpression(new FieldExpression(objectTypeA), "isInstance", new ArgumentListExpression(new VariableExpression[]{objectX})),
                        Token.newSymbol("&&", 0, 0),
                        new MethodCallExpression(new FieldExpression(objectTypeB), "isInstance", new ArgumentListExpression(new VariableExpression[]{objectY}))
                )
        );
        BooleanExpression invertedTypeCheck = new BooleanExpression(
                new BinaryExpression(
                        new MethodCallExpression(new FieldExpression(objectTypeA), "isInstance", new ArgumentListExpression(new VariableExpression[]{objectY})),
                        Token.newSymbol("&&", 0, 0),
                        new MethodCallExpression(new FieldExpression(objectTypeB), "isInstance", new ArgumentListExpression(new VariableExpression[]{objectX}))
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
        return classNode.getAllInterfaces().stream().anyMatch(it -> it.equals(new ClassNode(CollisionHandler.class)));
    }

}
