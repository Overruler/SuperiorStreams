package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleToLongFunction
 */
@FunctionalInterface
public interface DoubleToLongFunction extends ExDoubleToLongFunction<RuntimeException>, java.util.function.DoubleToLongFunction {
	default <E extends Exception> ExDoubleToLongFunction<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t) -> {
			try {
				return applyAsLong(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
