package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.DoubleFunction
 * @param <R>
 */
@FunctionalInterface
public interface DoubleFunction<R> extends ExDoubleFunction<R, RuntimeException>,
	java.util.function.DoubleFunction<R> {
	default <V> DoubleFunction<V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (double t) -> after.apply(apply(t));
	}
}
