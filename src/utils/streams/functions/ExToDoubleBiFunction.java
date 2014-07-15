package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.ToDoubleBiFunction
 * @param <T>
 * @param <U>
 * @param <E>
 */
@FunctionalInterface
public interface ExToDoubleBiFunction<T, U, E extends Exception> {
	/**
	 * @param t
	 * @param u
	 * @return double
	 * @throws E
	 * @see java.util.function.ToDoubleBiFunction#applyAsDouble
	 */
	double applyAsDouble(T t, U u) throws E;
	static <T, U, E extends Exception> ExToDoubleBiFunction<T, U, E> recheck(
		java.util.function.ToDoubleBiFunction<T, U> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t, U u) -> {
			try {
				return unchecked.applyAsDouble(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.ToDoubleBiFunction<T, U> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, U u) -> {
			try {
				return applyAsDouble(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
