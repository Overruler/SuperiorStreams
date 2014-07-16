package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleToIntFunction
 */
@FunctionalInterface
public interface DoubleToIntFunction extends ExDoubleToIntFunction<RuntimeException>, java.util.function.DoubleToIntFunction {
	default <E extends Exception> ExDoubleToIntFunction<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t) -> {
			try {
				return applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
