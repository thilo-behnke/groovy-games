package gep;

import groovy.lang.Tuple2;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.tb.gg.gameObject.PerishConditions;
import org.tb.gg.gameObject.Perishable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@GroovyASTTransformation
public class GlobalPerishConditionsASTTransformation extends AbstractASTTransformation {
    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        System.out.println("global");
        List<ClassNode> classesInSource = source.getAST().getClasses();
        List<Tuple2<ClassNode, Set<ClassNode>>> classesToInterfaces = classesInSource.stream()
                .map(clazz -> new Tuple2<>(clazz, clazz.getAllInterfaces()))
                .filter(it -> !it.getV1().isAbstract() && !it.getV1().isInterface() && !it.getV2().isEmpty())
                .collect(Collectors.toList());

        if (classesToInterfaces.isEmpty()) {
            return;
        }

        List<Tuple2<ClassNode, Set<ClassNode>>> perishableImplementations = classesToInterfaces.stream()
                .map(it -> {
                    Set<ClassNode> classNodeImpls = it.getV2().stream()
                            .filter(impl -> impl.getAllInterfaces().contains(new ClassNode(Perishable.class)))
                            .collect(Collectors.toSet());
                    return new Tuple2<>(it.getV1(), classNodeImpls);
                })
                .filter(it -> !it.getV2().isEmpty())
                .collect(Collectors.toList());

        perishableImplementations.forEach(it -> {
            it.getV1().addAnnotation(new AnnotationNode(new ClassNode(PerishConditions.class)));
        });
    }
}
