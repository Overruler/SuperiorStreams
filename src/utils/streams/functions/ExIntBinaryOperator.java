package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <E>
 * @see java.util.function.IntBinaryOperator
 */
@FunctionalInterface
public interface ExIntBinaryOperator<E extends Exception> {
	/**
	 * @param t
	 * @param u
	 * @return int
	 * @throws E
	 * @see java.util.function.IntBinaryOperator#applyAsInt
	 */
	int applyAsInt(int t, int u) throws E;
	static <E extends Exception> ExIntBinaryOperator<E> recheck(
		java.util.function.IntBinaryOperator unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (int t, int u) -> {
			try {
				return unchecked.applyAsInt(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default IntBinaryOperator uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t, int u) -> {
			try {
				return applyAsInt(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
