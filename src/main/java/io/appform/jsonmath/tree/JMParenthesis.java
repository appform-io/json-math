package io.appform.jsonmath.tree;

import lombok.Value;

/**
 *
 */
@Value
public class JMParenthesis implements JMTreeNode {
    JMTreeNode childExpression;

    public JMParenthesis(JMTreeNode childExpression) {
        this.childExpression = childExpression;
    }

    @Override
    public <T> T accept(JMTreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
