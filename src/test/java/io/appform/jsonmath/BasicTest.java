package io.appform.jsonmath;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static io.appform.jsonmath.JMTestUtils.jsonNode;
import static io.appform.jsonmath.JMTestUtils.match;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 *
 */
class BasicTest {

    private final JMEngine engine = JMEngine.builder().build();

    @ParameterizedTest
    @MethodSource("rules")
    @SneakyThrows
    void testParsing(final String rule, JsonNode data, double expectation) {
        match(expectation, engine.evaluate(rule, data));
    }

    private static Stream<Arguments> rules() {
        return Stream.of(
                arguments(named("BasicNumSingle", "4"), NullNode.getInstance(), 4),
                arguments(named("BasicNum", "4 * 7"), NullNode.getInstance(), 28),
                arguments(named("PriorityCheck1", "4 * 7 - 6 * 5 / 7"), NullNode.getInstance(), 23.714285714),
                arguments(named("RhsBracket", "4 * (8 - 6)"), NullNode.getInstance(), 8),
                arguments(named("LhsBracket", "(4 - 8) * 6"), NullNode.getInstance(), -24),
                arguments(named("BothBracket", "(4 - 8) * (6 - 8)"), NullNode.getInstance(), 8),
                arguments(named("Fraction", "0.5 * 2.5"), NullNode.getInstance(), 1.25),
                arguments(named("Negative", "0.5 + -2.5"), NullNode.getInstance(), -2),
                arguments(named("NegativeMinus", "0.5 - -2.5"), NullNode.getInstance(), -3),
                arguments(named("JsonPtr", "'/value' - -2.5"), jsonNode(Collections.singletonMap("value", 0.5)), -3),
                arguments(named("JsonPtrMissing", "'/value' - -2.5"), jsonNode(Collections.singletonMap("value", 0.5)), -2.5),
                arguments(named("JsonPtrComplicated", "'/a' * '/b' - '/c' * '/d' / '/e'"),
                          jsonNode(ImmutableMap.of("a",4, "b", 7, "c", 6, "d", 5, "e", 7)),
                          23.714285714),
                arguments(named("JsonPathComplicated", "'$.a' * '$.b' - '$.c' * '$.d' / '$.e'"),
                          jsonNode(ImmutableMap.of("a",4, "b", 7, "c", 6, "d", 5, "e", 7)),
                          23.714285714)
                        );
    }

}
