package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntToDoubleFunction
 */
@FunctionalInterface
public interface IntToDoubleFunction extends ExIntToDoubleFunction<RuntimeException>, java.util.function.IntToDoubleFunction {
	default <E extends Exception> ExIntToDoubleFunction<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t) -> {
			try {
				return applyAsDouble(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
