package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.UnaryOperator
 * @param <T>
 */
@FunctionalInterface
public interface UnaryOperator<T> extends ExUnaryOperator<T, RuntimeException>, Function<T, T>,
	java.util.function.UnaryOperator<T> {
	default UnaryOperator<T> compose(UnaryOperator<T> before) {
		Objects.requireNonNull(before);
		return (T t) -> apply(before.apply(t));
	}
	default UnaryOperator<T> andThen(UnaryOperator<T> after) {
		Objects.requireNonNull(after);
		return (T t) -> after.apply(apply(t));
	}
	static <T> UnaryOperator<T> identity() {
		return t -> t;
	}
}
