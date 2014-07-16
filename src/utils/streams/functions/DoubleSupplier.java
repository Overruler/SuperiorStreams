package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleSupplier
 */
@FunctionalInterface
public interface DoubleSupplier extends ExDoubleSupplier<RuntimeException>, java.util.function.DoubleSupplier {
	default <E extends Exception> ExDoubleSupplier<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return () -> {
			try {
				return getAsDouble();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
