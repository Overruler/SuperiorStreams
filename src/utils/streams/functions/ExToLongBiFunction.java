package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.ToLongBiFunction
 * @param <T>
 * @param <U>
 * @param <E>
 */
@FunctionalInterface
public interface ExToLongBiFunction<T, U, E extends Exception> {
	/**
	 * @param t
	 * @param u
	 * @return long
	 * @throws E
	 * @see java.util.function.ToLongBiFunction#applyAsLong
	 */
	long applyAsLong(T t, U u) throws E;
	static <T, U, E extends Exception> ExToLongBiFunction<T, U, E> recheck(
		java.util.function.ToLongBiFunction<T, U> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t, U u) -> {
			try {
				return unchecked.applyAsLong(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.ToLongBiFunction<T, U> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, U u) -> {
			try {
				return applyAsLong(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
