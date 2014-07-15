package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.ToLongFunction
 * @param <T>
 * @param <E>
 */
@FunctionalInterface
public interface ExToLongFunction<T, E extends Exception> {
	/**
	 * @param t
	 * @return long
	 * @throws E
	 * @see java.util.function.ToLongFunction#applyAsLong
	 */
	long applyAsLong(T t) throws E;
	static <T, E extends Exception> ExToLongFunction<T, E> recheck(
		java.util.function.ToLongFunction<T> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				return unchecked.applyAsLong(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.ToLongFunction<T> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return applyAsLong(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
