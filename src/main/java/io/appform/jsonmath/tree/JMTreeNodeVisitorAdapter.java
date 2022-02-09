package io.appform.jsonmath.tree;

import io.appform.jsonmath.value.JMFunction;
import io.appform.jsonmath.value.JMJsonField;
import io.appform.jsonmath.value.JMNumber;

/**
 *
 */
public class JMTreeNodeVisitorAdapter<T> implements JMTreeNodeVisitor<T> {
    private final T defaultValue;

    public JMTreeNodeVisitorAdapter(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public T visit(JMParenthesis parenthesis) {
        return defaultValue;
    }

    @Override
    public T visit(JMFunction function) {
        return defaultValue;
    }

    @Override
    public T visit(JMJsonField jsonField) {
        return defaultValue;
    }

    @Override
    public T visit(JMNumber number) {
        return defaultValue;
    }

    @Override
    public T visit(JMOperator operator) {
        return defaultValue;
    }
}
