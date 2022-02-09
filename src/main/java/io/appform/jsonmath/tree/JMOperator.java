package io.appform.jsonmath.tree;

import lombok.Data;

/**
 *
 */
@Data
public class JMOperator implements JMTreeNode {
    private final OperatorType operator;
    private JMTreeNode lhs = null;
    private JMTreeNode rhs = null;

    public JMOperator(OperatorType operator) {
        this.operator = operator;
    }

    @Override
    public <T> T accept(JMTreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
