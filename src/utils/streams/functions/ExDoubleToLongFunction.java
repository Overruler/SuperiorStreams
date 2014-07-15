package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleToLongFunction
 * @param <E>
 */
@FunctionalInterface
public interface ExDoubleToLongFunction<E extends Exception> {
	/**
	 * @param t
	 * @return long
	 * @throws E
	 * @see java.util.function.DoubleToLongFunction#applyAsLong
	 */
	long applyAsLong(double t) throws E;
	static <E extends Exception> ExDoubleToLongFunction<E> recheck(
		java.util.function.DoubleToLongFunction unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (double t) -> {
			try {
				return unchecked.applyAsLong(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.DoubleToLongFunction uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t) -> {
			try {
				return applyAsLong(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
