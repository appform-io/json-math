package io.appform.jsonmath;

import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import io.appform.jsonmath.parser.JsonMathParser;
import io.appform.jsonmath.parser.ParseException;
import io.appform.jsonmath.tree.JMTreeNode;
import lombok.Builder;
import lombok.val;

import java.io.StringReader;

/**
 *
 */
@Builder
public class JMEngine {
    private final FunctionRegistry functionRegistry = new FunctionRegistry();
    @Builder.Default
    private JMErrorHandlingStrategy errorHandlingStrategy = new DefaultJMErrorHandlingStrategyImpl();

    public JMTreeNode parse(final String rule) throws ParseException {
        val parser = new JsonMathParser(new StringReader(rule));
        return parser.parse(functionRegistry);
    }

    public double evaluate(final JMTreeNode node, final JsonNode jsonNode) {
        val jsonPath = JsonPath.using(Configuration.builder()
                                                  .jsonProvider(new JacksonJsonNodeJsonProvider())
                                                  .options(Option.SUPPRESS_EXCEPTIONS)
                                                  .build());
        val ctx = new JMEvalContext(jsonNode, jsonPath.parse(jsonNode), errorHandlingStrategy);
        return JMUtils.evaluate(ctx, node);
    }

    public double evaluate(final String rule, final JsonNode jsonNode) throws ParseException {
        return evaluate(parse(rule), jsonNode);
    }
}
