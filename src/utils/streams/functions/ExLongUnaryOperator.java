package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <E>
 * @see java.util.function.LongUnaryOperator
 */
@FunctionalInterface
public interface ExLongUnaryOperator<E extends Exception> {
	/**
	 * @param t
	 * @return long
	 * @throws E
	 * @see java.util.function.LongUnaryOperator#applyAsLong
	 */
	long applyAsLong(long t) throws E;
	default ExLongUnaryOperator<E> compose(ExLongUnaryOperator<E> before) {
		Objects.requireNonNull(before);
		return (long v) -> applyAsLong(before.applyAsLong(v));
	}
	default ExLongUnaryOperator<E> andThen(ExLongUnaryOperator<E> after) {
		Objects.requireNonNull(after);
		return (long t) -> after.applyAsLong(applyAsLong(t));
	}
	static <E extends Exception> ExLongUnaryOperator<E> identity() {
		return t -> t;
	}
	static <E extends Exception> ExLongUnaryOperator<E> recheck(
		java.util.function.LongUnaryOperator unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (long t) -> {
			try {
				return unchecked.applyAsLong(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default LongUnaryOperator uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t) -> {
			try {
				return applyAsLong(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
