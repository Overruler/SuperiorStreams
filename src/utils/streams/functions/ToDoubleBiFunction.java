package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @see java.util.function.ToDoubleBiFunction
 */
@FunctionalInterface
public interface ToDoubleBiFunction<T, U> extends ExToDoubleBiFunction<T, U, RuntimeException>, java.util.function.ToDoubleBiFunction<T, U> {
	default <E extends Exception> ExToDoubleBiFunction<T, U, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, U u) -> {
			try {
				return applyAsDouble(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
