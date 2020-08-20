package gep;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.tb.gg.gameObject.PerishCondition;
import org.tb.gg.gameObject.PerishConditions;

import java.util.List;
import java.util.stream.Collectors;

@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
public class GlobalPerishConditionsASTTransformation extends AbstractASTTransformation {
    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        List<ClassNode> perishableImplementations = getClassToInterfaceImplementationsMapping(source);

        perishableImplementations.forEach(it -> {
            it.addAnnotation(new AnnotationNode(new ClassNode(PerishConditions.class)));
        });
    }

    private List<ClassNode> getClassToInterfaceImplementationsMapping(SourceUnit source) {
        List<ClassNode> classesInSource = source.getAST().getClasses();
        return classesInSource.stream()
                .filter(it -> !it.isAbstract() && !it.isInterface())
                .filter(this::implementsPerishableCondition)
                .collect(Collectors.toList());
    }

    private boolean implementsPerishableCondition(ClassNode classNode) {
        return classNode.getAllInterfaces()
                .stream()
                .anyMatch(implementedInterface -> implementedInterface.getAnnotations().stream().anyMatch(anno -> anno.getClassNode().equals(new ClassNode(PerishCondition.class))));
    }
}
