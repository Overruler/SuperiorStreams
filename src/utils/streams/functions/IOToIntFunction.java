package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.ToIntFunction
 */
@FunctionalInterface
public interface IOToIntFunction<T> extends ExToIntFunction<T, IOException> {
	static <T> IOToIntFunction<T> recheck(java.util.function.ToIntFunction<T> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				return unchecked.applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default ToIntFunction<T> uncheck() {
		return (T t) -> {
			try {
				return applyAsInt(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
