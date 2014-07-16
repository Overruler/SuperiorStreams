package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntBinaryOperator
 */
@FunctionalInterface
public interface IntBinaryOperator extends ExIntBinaryOperator<RuntimeException>, java.util.function.IntBinaryOperator {
	default <E extends Exception> ExIntBinaryOperator<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t, int u) -> {
			try {
				return applyAsInt(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
