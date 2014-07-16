package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleUnaryOperator
 */
@FunctionalInterface
public interface DoubleUnaryOperator extends ExDoubleUnaryOperator<RuntimeException>, java.util.function.DoubleUnaryOperator {
	default DoubleUnaryOperator compose(DoubleUnaryOperator before) {
		Objects.requireNonNull(before);
		return (double v) -> applyAsDouble(before.applyAsDouble(v));
	}
	default DoubleUnaryOperator andThen(DoubleUnaryOperator after) {
		Objects.requireNonNull(after);
		return (double t) -> after.applyAsDouble(applyAsDouble(t));
	}
	static DoubleUnaryOperator identity() {
		return t -> t;
	}
	default <E extends Exception> ExDoubleUnaryOperator<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t) -> {
			try {
				return applyAsDouble(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
