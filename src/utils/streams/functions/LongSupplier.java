package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongSupplier
 */
@FunctionalInterface
public interface LongSupplier extends ExLongSupplier<RuntimeException>, java.util.function.LongSupplier {
	default <E extends Exception> ExLongSupplier<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return () -> {
			try {
				return getAsLong();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
