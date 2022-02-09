package io.appform.jsonmath;

import com.fasterxml.jackson.databind.node.JsonNodeType;

/**
 *
 */
public interface JMErrorHandlingStrategy {
    double handleMissingValue(String pointer);

    double dataTypeMismatch(String pointer, JsonNodeType nodeType);
}
