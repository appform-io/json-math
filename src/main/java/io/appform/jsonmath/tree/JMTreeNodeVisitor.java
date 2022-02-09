package io.appform.jsonmath.tree;

import io.appform.jsonmath.value.JMFunction;
import io.appform.jsonmath.value.JMJsonField;
import io.appform.jsonmath.value.JMNumber;

/**
 *
 */
public interface JMTreeNodeVisitor<T> {

    T visit(JMParenthesis parenthesis);

    T visit(JMFunction function);

    T visit(JMJsonField jsonField);

    T visit(JMNumber number);

    T visit(JMOperator operator);
}
