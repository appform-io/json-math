package io.appform.jsonmath.value;

import io.appform.jsonmath.tree.JMTreeNode;
import lombok.Data;

/**
 *
 */
@Data
public abstract class JMValue implements JMTreeNode {
    private final JMValueType type;

    protected JMValue(JMValueType type) {
        this.type = type;
    }
}
