package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @see java.util.function.ToIntBiFunction
 */
@FunctionalInterface
public interface ToIntBiFunction<T, U> extends ExToIntBiFunction<T, U, RuntimeException>, java.util.function.ToIntBiFunction<T, U> {
	default <E extends Exception> ExToIntBiFunction<T, U, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, U u) -> {
			try {
				return applyAsInt(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
