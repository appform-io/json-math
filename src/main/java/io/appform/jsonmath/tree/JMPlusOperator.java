package io.appform.jsonmath.tree;

/**
 *
 */
public class JMPlusOperator extends JMOperator {
    public JMPlusOperator() {
        super(OperatorType.PLUS);
    }

    @Override
    public <T> T accept(JMTreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
