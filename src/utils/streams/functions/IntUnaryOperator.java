package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntUnaryOperator
 */
@FunctionalInterface
public interface IntUnaryOperator extends ExIntUnaryOperator<RuntimeException>, java.util.function.IntUnaryOperator {
	default IntUnaryOperator compose(IntUnaryOperator before) {
		Objects.requireNonNull(before);
		return (int v) -> applyAsInt(before.applyAsInt(v));
	}
	default IntUnaryOperator andThen(IntUnaryOperator after) {
		Objects.requireNonNull(after);
		return (int t) -> after.applyAsInt(applyAsInt(t));
	}
	static IntUnaryOperator identity() {
		return t -> t;
	}
	default <E extends Exception> ExIntUnaryOperator<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t) -> {
			try {
				return applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
