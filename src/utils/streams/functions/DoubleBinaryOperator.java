package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleBinaryOperator
 */
@FunctionalInterface
public interface DoubleBinaryOperator extends ExDoubleBinaryOperator<RuntimeException>, java.util.function.DoubleBinaryOperator {
	default <E extends Exception> ExDoubleBinaryOperator<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t, double u) -> {
			try {
				return applyAsDouble(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
