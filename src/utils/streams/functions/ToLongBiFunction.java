package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @see java.util.function.ToLongBiFunction
 */
@FunctionalInterface
public interface ToLongBiFunction<T, U> extends ExToLongBiFunction<T, U, RuntimeException>, java.util.function.ToLongBiFunction<T, U> {
	default <E extends Exception> ExToLongBiFunction<T, U, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, U u) -> {
			try {
				return applyAsLong(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
