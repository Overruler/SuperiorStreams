package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.ToIntFunction
 */
@FunctionalInterface
public interface ToIntFunction<T> extends ExToIntFunction<T, RuntimeException>, java.util.function.ToIntFunction<T> {
	default <E extends Exception> ExToIntFunction<T, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
