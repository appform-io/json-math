package io.appform.jsonmath.value;

import io.appform.jsonmath.tree.JMTreeNode;
import io.appform.jsonmath.tree.JMTreeNodeVisitor;
import lombok.Value;

/**
 *
 */
@Value
public class JMJsonField implements JMTreeNode {
    public enum Type {
        POINTER,
        PATH
    };

    Type type;
    String pointer;

    public JMJsonField(Type type, String pointer) {
        this.type = type;
        this.pointer = pointer;
    }

    @Override
    public <T> T accept(JMTreeNodeVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
