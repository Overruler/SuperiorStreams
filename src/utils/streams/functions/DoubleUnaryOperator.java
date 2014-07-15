package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.DoubleUnaryOperator

 */
@FunctionalInterface
public interface DoubleUnaryOperator extends ExDoubleUnaryOperator<RuntimeException>, java.util.function.DoubleUnaryOperator {
	default DoubleUnaryOperator compose(DoubleUnaryOperator before) {
		Objects.requireNonNull(before);
		return (double v) -> applyAsDouble(before.applyAsDouble(v));
	}
	default DoubleUnaryOperator andThen(DoubleUnaryOperator after) {
		Objects.requireNonNull(after);
		return (double t) -> after.applyAsDouble(applyAsDouble(t));
	}
	static DoubleUnaryOperator identity() {
		return t -> t;
	}
}
