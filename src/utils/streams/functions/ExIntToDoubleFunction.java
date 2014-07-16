package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <E>
 * @see java.util.function.IntToDoubleFunction
 */
@FunctionalInterface
public interface ExIntToDoubleFunction<E extends Exception> {
	/**
	 * @param t
	 * @return double
	 * @throws E
	 * @see java.util.function.IntToDoubleFunction#applyAsDouble
	 */
	double applyAsDouble(int t) throws E;
	static <E extends Exception> ExIntToDoubleFunction<E> recheck(
		java.util.function.IntToDoubleFunction unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (int t) -> {
			try {
				return unchecked.applyAsDouble(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default IntToDoubleFunction uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t) -> {
			try {
				return applyAsDouble(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
