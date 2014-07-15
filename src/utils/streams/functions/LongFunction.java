package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.LongFunction
 * @param <R>
 */
@FunctionalInterface
public interface LongFunction<R> extends ExLongFunction<R, RuntimeException>,
	java.util.function.LongFunction<R> {
	default <V> LongFunction<V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (long t) -> after.apply(apply(t));
	}
}
