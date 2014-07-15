package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.LongUnaryOperator

 */
@FunctionalInterface
public interface LongUnaryOperator extends ExLongUnaryOperator<RuntimeException>, java.util.function.LongUnaryOperator {
	default LongUnaryOperator compose(LongUnaryOperator before) {
		Objects.requireNonNull(before);
		return (long v) -> applyAsLong(before.applyAsLong(v));
	}
	default LongUnaryOperator andThen(LongUnaryOperator after) {
		Objects.requireNonNull(after);
		return (long t) -> after.applyAsLong(applyAsLong(t));
	}
	static LongUnaryOperator identity() {
		return t -> t;
	}
}
