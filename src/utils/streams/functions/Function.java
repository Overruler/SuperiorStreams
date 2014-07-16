package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <R>
 * @see java.util.function.Function
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
	default <E extends Exception> ExFunction<T, R, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
