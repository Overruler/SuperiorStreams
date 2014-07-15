package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.Supplier
 * @param <T>
 * @param <E>
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
	default java.util.function.Supplier<T> uncheck(Class<E> classOfE) {
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
