package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <R>
 * @see java.util.function.Function
 */
@FunctionalInterface
public interface IOFunction<T, R> extends ExFunction<T, R, IOException> {
	default <V> IOFunction<V, R> compose(IOFunction<? super V, ? extends T> before) {
		Objects.requireNonNull(before);
		return (V v) -> apply(before.apply(v));
	}
	default <V> IOFunction<T, V> andThen(IOFunction<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (T t) -> after.apply(apply(t));
	}
	static <T> IOFunction<T, T> identity() {
		return t -> t;
	}
	static <T, R> IOFunction<T, R> recheck(java.util.function.Function<T, R> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				return unchecked.apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default Function<T, R> uncheck() {
		return (T t) -> {
			try {
				return apply(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
