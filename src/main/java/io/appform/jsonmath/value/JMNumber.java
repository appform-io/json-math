package io.appform.jsonmath.value;

import io.appform.jsonmath.tree.JMTreeNode;
import io.appform.jsonmath.tree.JMTreeNodeVisitor;
import lombok.Value;

/**
 *
 */
@Value
public class JMNumber implements JMTreeNode {
    double value;

    public JMNumber(double value) {
        this.value = value;
    }

    @Override
    public <T> T accept(JMTreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
