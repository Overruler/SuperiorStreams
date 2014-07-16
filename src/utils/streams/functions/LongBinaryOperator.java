package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongBinaryOperator
 */
@FunctionalInterface
public interface LongBinaryOperator extends ExLongBinaryOperator<RuntimeException>, java.util.function.LongBinaryOperator {
	default <E extends Exception> ExLongBinaryOperator<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t, long u) -> {
			try {
				return applyAsLong(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
