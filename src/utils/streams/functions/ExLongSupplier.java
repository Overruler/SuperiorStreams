package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <E>
 * @see java.util.function.LongSupplier
 */
@FunctionalInterface
public interface ExLongSupplier<E extends Exception> {
	/**
	 * @return long
	 * @throws E
	 * @see java.util.function.LongSupplier#getAsLong
	 */
	long getAsLong() throws E;
	static <E extends Exception> ExLongSupplier<E> recheck(
		java.util.function.LongSupplier unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return () -> {
			try {
				return unchecked.getAsLong();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default LongSupplier uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return () -> {
			try {
				return getAsLong();
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
