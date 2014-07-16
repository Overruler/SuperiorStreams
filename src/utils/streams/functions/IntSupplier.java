package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntSupplier
 */
@FunctionalInterface
public interface IntSupplier extends ExIntSupplier<RuntimeException>, java.util.function.IntSupplier {
	default <E extends Exception> ExIntSupplier<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return () -> {
			try {
				return getAsInt();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
