package gep;

import groovy.lang.Tuple2;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.tb.gg.gameObject.PerishConditionASTTransformation;
import org.tb.gg.gameObject.PerishConditions;
import org.tb.gg.gameObject.Perishable;

import javax.xml.transform.Source;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@GroovyASTTransformation(phase = CompilePhase.SEMANTIC_ANALYSIS)
public class GlobalPerishConditionsASTTransformation extends AbstractASTTransformation {
    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
//        System.out.println("global");
        List<Tuple2<ClassNode, Set<ClassNode>>> classesToInterfaces = getClassToInterfaceImplementationsMapping(source);
        if (classesToInterfaces.isEmpty()) {
            return;
        }

//        System.out.println(classesToInterfaces);

        List<Tuple2<ClassNode, Set<ClassNode>>> perishableImplementations = filterPerishableImplementations(classesToInterfaces);
        if (perishableImplementations.isEmpty()) {
            return;
        }

        System.out.println(perishableImplementations);
        perishableImplementations.forEach(it -> {
            it.getV1().addAnnotation(new AnnotationNode(new ClassNode(PerishConditions.class)));

//            ClassNode classNode = it.getV1();
//            Map className = it.getV1().getPlainNodeReference().getNodeMetaData();
//            sourceUnit.getSource().getURI();
            // Manually trigger AST transformation.
//            System.out.println(className);
//            SourceUnit itSourceUnit = new SourceUnit(className, sourceUnit.getSource(), sourceUnit.getConfiguration(), sourceUnit.getClassLoader(), sourceUnit.getErrorCollector());
//            System.out.println("test2");
//            perishConditionTransformation.visit(
//                    new ASTNode[]{new AnnotationNode(new ClassNode(PerishConditions.class)), classNode},
//                    itSourceUnit
//            );
        });
    }

    private List<Tuple2<ClassNode, Set<ClassNode>>> getClassToInterfaceImplementationsMapping(SourceUnit source) {
        List<ClassNode> classesInSource = source.getAST().getClasses();
        return classesInSource.stream()
                .map(clazz -> new Tuple2<>(clazz, clazz.getAllInterfaces()))
                .filter(it -> !it.getV1().isAbstract() && !it.getV1().isInterface() && !it.getV2().isEmpty())
                .collect(Collectors.toList());
    }

    private List<Tuple2<ClassNode, Set<ClassNode>>> filterPerishableImplementations(List<Tuple2<ClassNode, Set<ClassNode>>> classesToInterfaces) {
        return classesToInterfaces.stream()
                .map(it -> {
                    Set<ClassNode> classNodeImpls = it.getV2().stream()
                            .filter(implementedInterface -> {
                                if (!implementedInterface.getAllInterfaces().contains(new ClassNode(Perishable.class))) {
                                    return false;
                                }
                                if (implementedInterface.getAllInterfaces().stream().filter(impl -> impl.implementsInterface(new ClassNode(Perishable.class))).count() < 2) {
                                   return false;
                                }
                                if (implementedInterface.equals(new ClassNode(Perishable.class))) {
                                    return false;
                                }
                                System.out.println("####");
                                System.out.println(it);
                                System.out.println(implementedInterface);
                                System.out.println(implementedInterface.getAllInterfaces());
                                return true;
//                                Class<?> implementedInterfaceClass = implementedInterface.getTypeClass();
//                                return !implementedInterfaceClass.equals(Perishable.class) && Perishable.class.isAssignableFrom(implementedInterfaceClass);
                            })
                            .collect(Collectors.toSet());
                    return new Tuple2<>(it.getV1(), classNodeImpls);
                })
                .filter(it -> !it.getV2().isEmpty())
                .collect(Collectors.toList());
    }
}
