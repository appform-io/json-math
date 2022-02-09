package io.appform.jsonmath;

import com.fasterxml.jackson.databind.node.JsonNodeType;

/**
 *
 */
public class DefaultJMErrorHandlingStrategyImpl implements JMErrorHandlingStrategy {
    @Override
    public double handleMissingValue(String pointer) {
        return 0;
    }

    @Override
    public double dataTypeMismatch(String pointer, JsonNodeType nodeType) {
        return 0;
    }
}
