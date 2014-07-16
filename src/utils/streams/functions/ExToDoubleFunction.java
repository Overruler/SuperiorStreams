package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <E>
 * @see java.util.function.ToDoubleFunction
 */
@FunctionalInterface
public interface ExToDoubleFunction<T, E extends Exception> {
	/**
	 * @param t
	 * @return double
	 * @throws E
	 * @see java.util.function.ToDoubleFunction#applyAsDouble
	 */
	double applyAsDouble(T t) throws E;
	static <T, E extends Exception> ExToDoubleFunction<T, E> recheck(
		java.util.function.ToDoubleFunction<T> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				return unchecked.applyAsDouble(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default ToDoubleFunction<T> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return applyAsDouble(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
