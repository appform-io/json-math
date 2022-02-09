package io.appform.jsonmath;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.DocumentContext;
import io.appform.jsonmath.value.JMJsonField;
import lombok.Value;

import java.util.HashMap;

/**
 *
 */
@Value
public class JMEvalContext {
    JsonNode node;
    DocumentContext document;
    JMErrorHandlingStrategy strategy;
    HashMap<JMJsonField, JsonNode> nodeCache = new HashMap<>();

    public JMEvalContext(JsonNode node, DocumentContext document, JMErrorHandlingStrategy strategy) {
        this.node = node;
        this.document = document;
        this.strategy = strategy;
    }
}
