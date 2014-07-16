package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.ToLongFunction
 */
@FunctionalInterface
public interface ToLongFunction<T> extends ExToLongFunction<T, RuntimeException>, java.util.function.ToLongFunction<T> {
	default <E extends Exception> ExToLongFunction<T, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return applyAsLong(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
