package io.appform.jsonmath;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Preconditions;
import io.appform.jsonmath.tree.*;
import io.appform.jsonmath.value.JMFunction;
import io.appform.jsonmath.value.JMJsonField;
import io.appform.jsonmath.value.JMNumber;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@UtilityClass
public class JMUtils {
    public double evaluate(final JMEvalContext context, final JMTreeNode value) {
        return value.accept(new JMTreeNodeVisitor<Double>() {
            @Override
            public Double visit(JMNumber jmNumber) {
                return jmNumber.getValue();
            }

            @Override
            public Double visit(JMOperator operator) {
                val lhsValue = evaluate(context, operator.getLhs());
                val rhsValue = evaluate(context, operator.getRhs());

                return operator.getOperator().accept(new OperatorType.OpTypeVisitor<Double>() {
                    @Override
                    public Double visitPlus() {
                        return lhsValue + rhsValue;
                    }

                    @Override
                    public Double visitMinus() {
                        return lhsValue - rhsValue;
                    }

                    @Override
                    public Double visitMultiply() {
                        return lhsValue * rhsValue;
                    }

                    @Override
                    public Double visitDivide() {
                        return lhsValue / rhsValue;
                    }

                    @Override
                    public Double visitModulo() {
                        return lhsValue % rhsValue;
                    }

                    @Override
                    public Double visitPower() {
                        return Math.pow(lhsValue, rhsValue);
                    }
                });
            }

            @Override
            public Double visit(JMJsonField jmJsonField) {
                val pointer = jmJsonField.getPointer();
                val node = jsonNode(context, jmJsonField);
                if(null == node || node.isMissingNode() || node.isNull()) {
                    return context.getStrategy().handleMissingValue(pointer);
                }
                if(!node.isNumber()) {
                    return context.getStrategy().dataTypeMismatch(pointer, node.getNodeType());
                }
                return node.asDouble();
            }

            @Override
            public Double visit(JMParenthesis parenthesis) {
                return evaluate(context, parenthesis.getChildExpression());
            }

            @Override
            public Double visit(JMFunction jmJsonFunction) {
//                return jmJsonFunction.evaluate();
                return 0.0; //TODO::IMPLEMENT
            }

        });
    }

    public JMFunction function(final FunctionRegistry registry, final String name, final List<JMTreeNode> params) {
        final FunctionRegistry.FunctionMeta functionMeta = registry.find(name)
                .orElse(null);
        if (null == functionMeta) {
            throw new IllegalArgumentException("Unknown function '" + name + "'");
        }
        final List<FunctionRegistry.ConstructorMeta> constructors = functionMeta.getConstructors();
        if(constructors.stream().anyMatch(FunctionRegistry.ConstructorMeta::isHasVariableArgs)) {
            return new JMFunction(name, params, constructors.get(0));
        }
        final int numProvidedParams = params.size();
        final List<FunctionRegistry.ConstructorMeta> matchingConstructors = constructors.stream()
                .filter(constructorMeta -> constructorMeta.getParamTypes()
                        .size() == numProvidedParams)
                .collect(Collectors.toList());
        Preconditions.checkArgument(!matchingConstructors.isEmpty(),
                                    String.format("No matching function named %s that accepts %d params.",
                                                  name, numProvidedParams));
        Preconditions.checkArgument(matchingConstructors.size() == 1,
                                    "Function " + name + " seems to have more than one matching overload." +
                                            " Cannot resolve.");

        return new JMFunction(name, params, constructors.get(0));
    }

    public JMTreeNode node(final OperatorType operator, final JMTreeNode lhs, final JMTreeNode rhs) {
//        log.debug("operator = {} lhs = {} rhs = = {}");
        val newOp = new JMOperator(operator);
        JMOperator opNode = lhs.accept(new JMTreeNodeVisitorFunctionAdapter<JMOperator>(node -> {
            newOp.setLhs(lhs);
            return newOp;
        }) {
            @Override
            public JMOperator visit(JMOperator operator) {
                if (operator.getOperator().getPrecedence() > newOp.getOperator().getPrecedence()) {
                    newOp.setLhs(operator.getRhs());
                    operator.setRhs(newOp);
                    return operator;
                }
                else {
                    newOp.setLhs(operator);
                }
                return newOp;
            }
        });
        JMOperator finalOpNode = opNode;
        opNode = rhs.accept(new JMTreeNodeVisitorFunctionAdapter<JMOperator>(node -> {
            finalOpNode.setRhs(rhs);
            return newOp;
        }) {
            @Override
            public JMOperator visit(JMOperator operator) {
                if(operator.getOperator().getPrecedence() < newOp.getOperator().getPrecedence()) {
                    finalOpNode.setRhs(operator.getLhs());
                    operator.setLhs(finalOpNode);
                    return operator;
                }
                else {
                    finalOpNode.setRhs(operator);
                }
                return finalOpNode;
            }
        });
        return opNode;
    }

    private void setOpNode(JMOperator operatorNode, JMOperator currOpNode) {
        if(currOpNode.getOperator().getPrecedence() > operatorNode.getOperator().getPrecedence()) {
            currOpNode.setLhs(operatorNode.getRhs());
            operatorNode.setRhs(currOpNode);
        }
        else {
            currOpNode.setLhs(operatorNode);
        }
    }

    private JsonNode jsonNode(final JMEvalContext context, final JMJsonField jsonField) {
        return context.getNodeCache()
                .computeIfAbsent(jsonField, f -> jsonNodeImpl(context, f));
    }

    private JsonNode jsonNodeImpl(final JMEvalContext context, final JMJsonField jsonField) {
        switch (jsonField.getType()) {
            case POINTER: return context.getNode().at(jsonField.getPointer());
            case PATH: return context.getDocument().read(jsonField.getPointer());
        }
        throw new IllegalArgumentException("Unsupported json mode");
    }
}
