package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleFunction
 * @param <R>
 */
@FunctionalInterface
public interface IODoubleFunction<R> extends ExDoubleFunction<R, IOException> {
	default <V> IODoubleFunction<V> andThen(IOFunction<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (double t) -> after.apply(apply(t));
	}
	static <R> IODoubleFunction<R> recheck(java.util.function.DoubleFunction<R> unchecked) {
		Objects.requireNonNull(unchecked);
		return (double t) -> {
			try {
				return unchecked.apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.DoubleFunction<R> uncheck() {
		return (double t) -> {
			try {
				return apply(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
