package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.IntFunction
 * @param <R>
 */
@FunctionalInterface
public interface IntFunction<R> extends ExIntFunction<R, RuntimeException>,
	java.util.function.IntFunction<R> {
	default <V> IntFunction<V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (int t) -> after.apply(apply(t));
	}
}
