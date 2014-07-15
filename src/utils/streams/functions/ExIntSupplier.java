package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntSupplier
 * @param <E>
 */
@FunctionalInterface
public interface ExIntSupplier<E extends Exception> {
	/**
	 * @return int
	 * @throws E
	 * @see java.util.function.IntSupplier#getAsInt
	 */
	int getAsInt() throws E;
	static <E extends Exception> ExIntSupplier<E> recheck(
		java.util.function.IntSupplier unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return () -> {
			try {
				return unchecked.getAsInt();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.IntSupplier uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return () -> {
			try {
				return getAsInt();
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
