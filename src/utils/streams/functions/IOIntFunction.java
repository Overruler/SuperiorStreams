package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntFunction
 * @param <R>
 */
@FunctionalInterface
public interface IOIntFunction<R> extends ExIntFunction<R, IOException> {
	default <V> IOIntFunction<V> andThen(IOFunction<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (int t) -> after.apply(apply(t));
	}
	static <R> IOIntFunction<R> recheck(java.util.function.IntFunction<R> unchecked) {
		Objects.requireNonNull(unchecked);
		return (int t) -> {
			try {
				return unchecked.apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.IntFunction<R> uncheck() {
		return (int t) -> {
			try {
				return apply(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
