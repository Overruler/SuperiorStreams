package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntToLongFunction
 */
@FunctionalInterface
public interface IntToLongFunction extends ExIntToLongFunction<RuntimeException>, java.util.function.IntToLongFunction {
	default <E extends Exception> ExIntToLongFunction<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t) -> {
			try {
				return applyAsLong(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
