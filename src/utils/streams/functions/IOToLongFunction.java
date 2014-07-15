package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.ToLongFunction
 * @param <T>
 */
@FunctionalInterface
public interface IOToLongFunction<T> extends ExToLongFunction<T, IOException> {
	static <T> IOToLongFunction<T> recheck(java.util.function.ToLongFunction<T> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				return unchecked.applyAsLong(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.ToLongFunction<T> uncheck() {
		return (T t) -> {
			try {
				return applyAsLong(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
