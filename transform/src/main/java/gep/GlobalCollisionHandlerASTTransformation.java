package gep;

import groovyjarjarasm.asm.Opcodes;
import org.codehaus.groovy.ast.*;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
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
        System.out.println(collisionHandlers);

        collisionHandlers.forEach(collisionHandler -> {
            addGenericParametersToClass(collisionHandler);
//            addInverseImplementationCheckToHandleCollision(collisionHandler);
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
        collisionHandler.addMethod(handleCollisionImplementation);

        Statement inverseImplementationCheck = createInverseImplementationCheck(collisionHandler);
        MethodNode handleCollisionInverseCheck = new MethodNode("handleCollision", handleCollision.getModifiers(), handleCollision.getReturnType(), handleCollision.getParameters(), handleCollision.getExceptions(), inverseImplementationCheck);
        collisionHandler.addMethod(handleCollisionInverseCheck);
    }

    private Statement createInverseImplementationCheck(ClassNode collisionHandler) {
//        BooleanExpression defaultCase = new BooleanExpression(new BinaryExpression());
        return new ReturnStatement(new ConstantExpression(false));
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
