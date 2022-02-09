package io.appform.jsonmath.tree;

import lombok.Getter;

/**
 *
 */
public enum OperatorType {

    MODULO(1) {
        @Override
        public <T> T accept(OpTypeVisitor<T> visitor) {
            return visitor.visitModulo();
        }
    },
    MINUS(2)  {
        @Override
        public <T> T accept(OpTypeVisitor<T> visitor) {
            return visitor.visitMinus();
        }
    },
    PLUS(3) {
        @Override
        public <T> T accept(OpTypeVisitor<T> visitor) {
            return visitor.visitPlus();
        }
    },
    MULTIPLY(4) {
        @Override
        public <T> T accept(OpTypeVisitor<T> visitor) {
            return visitor.visitMultiply();
        }
    },
    DIVIDE(5) {
        @Override
        public <T> T accept(OpTypeVisitor<T> visitor) {
            return visitor.visitDivide();
        }
    },
    POWER(6) {
        @Override
        public <T> T accept(OpTypeVisitor<T> visitor) {
            return visitor.visitPower();
        }
    },
    ;

    @Getter
    private final int precedence;

    public abstract <T> T accept(final OpTypeVisitor<T> visitor);

    OperatorType(int precedence) {
        this.precedence = precedence;
    }

    public interface OpTypeVisitor<T> {
        T visitPlus();
        T visitMinus();
        T visitMultiply();
        T visitDivide();
        T visitModulo();
        T visitPower();
    }

    public static OperatorType from(final String repr) {
        switch (repr) {
            case "+": return PLUS;
            case "-": return MINUS;
            case "*": return MULTIPLY;
            case "/": return DIVIDE;
            case "%": return MODULO;
            case "^": return POWER;
            default: throw new IllegalArgumentException("No known operator for: " + repr);
        }
    }
}
