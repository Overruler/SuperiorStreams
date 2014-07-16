package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <E>
 * @see java.util.function.ToIntFunction
 */
@FunctionalInterface
public interface ExToIntFunction<T, E extends Exception> {
	/**
	 * @param t
	 * @return int
	 * @throws E
	 * @see java.util.function.ToIntFunction#applyAsInt
	 */
	int applyAsInt(T t) throws E;
	static <T, E extends Exception> ExToIntFunction<T, E> recheck(
		java.util.function.ToIntFunction<T> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				return unchecked.applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default ToIntFunction<T> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return applyAsInt(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
