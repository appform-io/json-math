package io.appform.jsonmath;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.experimental.UtilityClass;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 */
@UtilityClass
public class JMTestUtils {
    private static final double MIN_MATCH_VALUE = 0.00001;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
    public static void match(double expected, double provided) {
        assertTrue(expected - provided < MIN_MATCH_VALUE);
    }

    public static JsonNode jsonNode(final Object node) {
        return MAPPER.valueToTree(node);
    }
}
