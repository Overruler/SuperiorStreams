package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.BiFunction
 * @param <T>
 * @param <U>
 * @param <R>
 */
@FunctionalInterface
public interface BiFunction<T, U, R> extends ExBiFunction<T, U, R, RuntimeException>,
	java.util.function.BiFunction<T, U, R> {
	default <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (T t, U u) -> after.apply(apply(t, u));
	}
}
