package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.ToDoubleFunction
 */
@FunctionalInterface
public interface ToDoubleFunction<T> extends ExToDoubleFunction<T, RuntimeException>, java.util.function.ToDoubleFunction<T> {
	default <E extends Exception> ExToDoubleFunction<T, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return applyAsDouble(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
