package io.appform.jsonmath;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.appform.jsonmath.JMTestUtils.jsonNode;
import static io.appform.jsonmath.JMTestUtils.match;
import static org.junit.jupiter.api.Named.named;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 *
 */
@Slf4j
class PerfTest {
    private final JMEngine engine = JMEngine.builder().build();

    @ParameterizedTest
    @MethodSource("rules")
    @SneakyThrows
    void testParsing(final String rule, JsonNode data, double expectation) {
        log.info("Rule: {}", rule);
        val r = engine.parse(rule);
        IntStream.rangeClosed(1, 10)
                .forEach(j -> {
                    val stopwatch = Stopwatch.createStarted();
                    IntStream.rangeClosed(1, 1_000_000).forEach(i -> match(expectation, engine.evaluate(r, data)));
                    log.info("Time taken for iteration {} : {} ms", j, stopwatch.elapsed(TimeUnit.MILLISECONDS));
                });
    }

    private static Stream<Arguments> rules() {
        return Stream.of(
                arguments(named("JsonPtrComplicated", "'/a' * '/b' - '/c' * '/d' / '/e'"),
                          jsonNode(ImmutableMap.of("a", 4, "b", 7, "c", 6, "d", 5, "e", 7)),
                          23.714285714),
                arguments(named("JsonPathComplicated", "'$.a' * '$.b' - '$.c' * '$.d' / '$.e'"),
                          jsonNode(ImmutableMap.of("a",4, "b", 7, "c", 6, "d", 5, "e", 7)),
                          23.714285714)
                        );
    }
}
