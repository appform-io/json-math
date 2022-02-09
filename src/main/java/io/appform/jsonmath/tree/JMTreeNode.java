package io.appform.jsonmath.tree;

/**
 *
 */
public interface JMTreeNode {
    public abstract <T> T accept(final JMTreeNodeVisitor<T> visitor);
}
