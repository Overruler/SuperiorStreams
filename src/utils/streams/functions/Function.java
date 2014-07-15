package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.Function
 * @param <T>
 * @param <R>
 */
@FunctionalInterface
public interface Function<T, R> extends ExFunction<T, R, RuntimeException>, java.util.function.Function<T, R> {
	default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return (V v) -> apply(before.apply(v));
	}
	default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (T t) -> after.apply(apply(t));
	}
	static <T> Function<T, T> identity() {
		return t -> t;
	}
}
