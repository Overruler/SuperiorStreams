package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @param <R>
 * @see java.util.function.BiFunction
 */
@FunctionalInterface
public interface IOBiFunction<T, U, R> extends ExBiFunction<T, U, R, IOException> {
	default <V> IOBiFunction<T, U, V> andThen(IOFunction<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (T t, U u) -> after.apply(apply(t, u));
	}
	static <T, U, R> IOBiFunction<T, U, R> recheck(java.util.function.BiFunction<T, U, R> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t, U u) -> {
			try {
				return unchecked.apply(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default BiFunction<T, U, R> uncheck() {
		return (T t, U u) -> {
			try {
				return apply(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
