package io.appform.jsonmath.tree;

/**
 *
 */
public class JMMinusOperator extends JMOperator {
    public JMMinusOperator() {
        super(OperatorType.MINUS);
    }

    @Override
    public <T> T accept(JMTreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
