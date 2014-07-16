package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongToDoubleFunction
 */
@FunctionalInterface
public interface LongToDoubleFunction extends ExLongToDoubleFunction<RuntimeException>, java.util.function.LongToDoubleFunction {
	default <E extends Exception> ExLongToDoubleFunction<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t) -> {
			try {
				return applyAsDouble(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
