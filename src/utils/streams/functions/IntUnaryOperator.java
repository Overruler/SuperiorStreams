package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.IntUnaryOperator

 */
@FunctionalInterface
public interface IntUnaryOperator extends ExIntUnaryOperator<RuntimeException>, java.util.function.IntUnaryOperator {
	default IntUnaryOperator compose(IntUnaryOperator before) {
		Objects.requireNonNull(before);
		return (int v) -> applyAsInt(before.applyAsInt(v));
	}
	default IntUnaryOperator andThen(IntUnaryOperator after) {
		Objects.requireNonNull(after);
		return (int t) -> after.applyAsInt(applyAsInt(t));
	}
	static IntUnaryOperator identity() {
		return t -> t;
	}
}
