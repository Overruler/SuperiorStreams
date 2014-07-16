package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <E>
 * @see java.util.function.Supplier
 */
@FunctionalInterface
public interface ExSupplier<T, E extends Exception> {
	/**
	 * @return T
	 * @throws E
	 * @see java.util.function.Supplier#get
	 */
	T get() throws E;
	static <T, E extends Exception> ExSupplier<T, E> recheck(
		java.util.function.Supplier<T> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return () -> {
			try {
				return unchecked.get();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default Supplier<T> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return () -> {
			try {
				return get();
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
