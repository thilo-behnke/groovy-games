package gep;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

import java.util.List;
import java.util.stream.Collectors;

@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
public class GlobalCollisionHandlerASTTransformation extends AbstractASTTransformation {

    private static final String COLLISION_HANDLER = "CollisionHandler";

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        List<ClassNode> collisionHandlers = findCollisionHandlers(source);
        if (collisionHandlers.isEmpty()) {
            return;
        }
        System.out.println(collisionHandlers);
    }

    private List<ClassNode> findCollisionHandlers(SourceUnit source) {
        return source.getAST().getClasses().stream()
                .filter(this::isCollisionHandler)
                .collect(Collectors.toList());
    }

    private boolean isCollisionHandler(ClassNode classNode) {
        // TODO: It would be better to use the class here, but this would mean moving more files to the transform sub project or using a different build process.
        String className = classNode.getPlainNodeReference().getTypeClass().getSimpleName();
        return !className.equals(COLLISION_HANDLER) && className.endsWith(COLLISION_HANDLER);
    }

}
