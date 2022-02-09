package io.appform.jsonmath.value;

import io.appform.jsonmath.FunctionRegistry;
import io.appform.jsonmath.tree.JMTreeNode;
import io.appform.jsonmath.tree.JMTreeNodeVisitor;
import lombok.Value;

import java.util.List;

/**
 *
 */
@Value
public class JMFunction implements JMTreeNode {
    String name;
    List<JMTreeNode> parameters;
    FunctionRegistry.ConstructorMeta selectedConstructor;

    public JMFunction(
            String name,
            List<JMTreeNode> parameters,
            FunctionRegistry.ConstructorMeta selectedConstructor) {
        this.name = name;
        this.parameters = parameters;
        this.selectedConstructor = selectedConstructor;
    }

    @Override
    public <T> T accept(JMTreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
