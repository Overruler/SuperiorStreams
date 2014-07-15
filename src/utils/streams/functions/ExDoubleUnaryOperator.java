package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleUnaryOperator
 * @param <E>
 */
@FunctionalInterface
public interface ExDoubleUnaryOperator<E extends Exception> {
	/**
	 * @param t
	 * @return double
	 * @throws E
	 * @see java.util.function.DoubleUnaryOperator#applyAsDouble
	 */
	double applyAsDouble(double t) throws E;
	default ExDoubleUnaryOperator<E> compose(ExDoubleUnaryOperator<E> before) {
		Objects.requireNonNull(before);
		return (double v) -> applyAsDouble(before.applyAsDouble(v));
	}
	default ExDoubleUnaryOperator<E> andThen(ExDoubleUnaryOperator<E> after) {
		Objects.requireNonNull(after);
		return (double t) -> after.applyAsDouble(applyAsDouble(t));
	}
	static <E extends Exception> ExDoubleUnaryOperator<E> identity() {
		return t -> t;
	}
	static <E extends Exception> ExDoubleUnaryOperator<E> recheck(
		java.util.function.DoubleUnaryOperator unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (double t) -> {
			try {
				return unchecked.applyAsDouble(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.DoubleUnaryOperator uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t) -> {
			try {
				return applyAsDouble(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
