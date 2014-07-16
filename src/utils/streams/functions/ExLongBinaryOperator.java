package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <E>
 * @see java.util.function.LongBinaryOperator
 */
@FunctionalInterface
public interface ExLongBinaryOperator<E extends Exception> {
	/**
	 * @param t
	 * @param u
	 * @return long
	 * @throws E
	 * @see java.util.function.LongBinaryOperator#applyAsLong
	 */
	long applyAsLong(long t, long u) throws E;
	static <E extends Exception> ExLongBinaryOperator<E> recheck(
		java.util.function.LongBinaryOperator unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (long t, long u) -> {
			try {
				return unchecked.applyAsLong(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default LongBinaryOperator uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t, long u) -> {
			try {
				return applyAsLong(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
