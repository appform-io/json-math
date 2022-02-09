package io.appform.jsonmath.tree;

import io.appform.jsonmath.value.JMFunction;
import io.appform.jsonmath.value.JMJsonField;
import io.appform.jsonmath.value.JMNumber;

import java.util.function.Function;

/**
 *
 */
public class JMTreeNodeVisitorFunctionAdapter<T> implements JMTreeNodeVisitor<T> {
    private final Function<JMTreeNode, T> defaultHandler;

    public JMTreeNodeVisitorFunctionAdapter(
            Function<JMTreeNode, T> defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    @Override
    public T visit(JMParenthesis parenthesis) {
        return defaultHandler.apply(parenthesis);
    }

    @Override
    public T visit(JMFunction function) {
        return defaultHandler.apply(function);
    }

    @Override
    public T visit(JMJsonField jsonField) {
        return defaultHandler.apply(jsonField);
    }

    @Override
    public T visit(JMNumber number) {
        return defaultHandler.apply(number);
    }

    @Override
    public T visit(JMOperator operator) {
        return defaultHandler.apply(operator);
    }
}
