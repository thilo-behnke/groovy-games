package org.tb.gg.gameObject;

import groovy.transform.CompileStatic;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@SuppressWarnings("unused")
@GroovyASTTransformation()
public class PerishConditionASTTransformation extends AbstractASTTransformation {
    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        System.out.println(source.getName());
    }
}
