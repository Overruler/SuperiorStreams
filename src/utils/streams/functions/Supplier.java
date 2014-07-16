package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.Supplier
 */
@FunctionalInterface
public interface Supplier<T> extends ExSupplier<T, RuntimeException>, java.util.function.Supplier<T> {
	default <E extends Exception> ExSupplier<T, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return () -> {
			try {
				return get();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
