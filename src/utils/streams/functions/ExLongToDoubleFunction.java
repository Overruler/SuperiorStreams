package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <E>
 * @see java.util.function.LongToDoubleFunction
 */
@FunctionalInterface
public interface ExLongToDoubleFunction<E extends Exception> {
	/**
	 * @param t
	 * @return double
	 * @throws E
	 * @see java.util.function.LongToDoubleFunction#applyAsDouble
	 */
	double applyAsDouble(long t) throws E;
	static <E extends Exception> ExLongToDoubleFunction<E> recheck(
		java.util.function.LongToDoubleFunction unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (long t) -> {
			try {
				return unchecked.applyAsDouble(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default LongToDoubleFunction uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t) -> {
			try {
				return applyAsDouble(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
