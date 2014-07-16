package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @param <E>
 * @see java.util.function.ToIntBiFunction
 */
@FunctionalInterface
public interface ExToIntBiFunction<T, U, E extends Exception> {
	/**
	 * @param t
	 * @param u
	 * @return int
	 * @throws E
	 * @see java.util.function.ToIntBiFunction#applyAsInt
	 */
	int applyAsInt(T t, U u) throws E;
	static <T, U, E extends Exception> ExToIntBiFunction<T, U, E> recheck(
		java.util.function.ToIntBiFunction<T, U> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t, U u) -> {
			try {
				return unchecked.applyAsInt(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default ToIntBiFunction<T, U> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, U u) -> {
			try {
				return applyAsInt(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
