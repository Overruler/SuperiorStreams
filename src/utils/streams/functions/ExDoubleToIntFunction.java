package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <E>
 * @see java.util.function.DoubleToIntFunction
 */
@FunctionalInterface
public interface ExDoubleToIntFunction<E extends Exception> {
	/**
	 * @param t
	 * @return int
	 * @throws E
	 * @see java.util.function.DoubleToIntFunction#applyAsInt
	 */
	int applyAsInt(double t) throws E;
	static <E extends Exception> ExDoubleToIntFunction<E> recheck(
		java.util.function.DoubleToIntFunction unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (double t) -> {
			try {
				return unchecked.applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default DoubleToIntFunction uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t) -> {
			try {
				return applyAsInt(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
