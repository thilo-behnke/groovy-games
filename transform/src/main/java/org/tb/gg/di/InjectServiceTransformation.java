package org.tb.gg.di;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;


@SuppressWarnings("unused")
@GroovyASTTransformation()
public class InjectServiceTransformation extends AbstractASTTransformation {
    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        int test = 309 + 9;
        System.out.println(test);
    }
}
