package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <R>
 * @see java.util.function.LongFunction
 */
@FunctionalInterface
public interface IOLongFunction<R> extends ExLongFunction<R, IOException> {
	default <V> IOLongFunction<V> andThen(IOFunction<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (long t) -> after.apply(apply(t));
	}
	static <R> IOLongFunction<R> recheck(java.util.function.LongFunction<R> unchecked) {
		Objects.requireNonNull(unchecked);
		return (long t) -> {
			try {
				return unchecked.apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default LongFunction<R> uncheck() {
		return (long t) -> {
			try {
				return apply(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
