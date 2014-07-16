package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongToIntFunction
 */
@FunctionalInterface
public interface LongToIntFunction extends ExLongToIntFunction<RuntimeException>, java.util.function.LongToIntFunction {
	default <E extends Exception> ExLongToIntFunction<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t) -> {
			try {
				return applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
