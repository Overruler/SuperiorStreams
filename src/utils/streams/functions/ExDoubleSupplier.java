package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <E>
 * @see java.util.function.DoubleSupplier
 */
@FunctionalInterface
public interface ExDoubleSupplier<E extends Exception> {
	/**
	 * @return double
	 * @throws E
	 * @see java.util.function.DoubleSupplier#getAsDouble
	 */
	double getAsDouble() throws E;
	static <E extends Exception> ExDoubleSupplier<E> recheck(
		java.util.function.DoubleSupplier unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return () -> {
			try {
				return unchecked.getAsDouble();
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default DoubleSupplier uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return () -> {
			try {
				return getAsDouble();
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
