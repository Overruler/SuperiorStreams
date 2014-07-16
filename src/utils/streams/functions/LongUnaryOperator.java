package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongUnaryOperator
 */
@FunctionalInterface
public interface LongUnaryOperator extends ExLongUnaryOperator<RuntimeException>, java.util.function.LongUnaryOperator {
	default LongUnaryOperator compose(LongUnaryOperator before) {
		Objects.requireNonNull(before);
		return (long v) -> applyAsLong(before.applyAsLong(v));
	}
	default LongUnaryOperator andThen(LongUnaryOperator after) {
		Objects.requireNonNull(after);
		return (long t) -> after.applyAsLong(applyAsLong(t));
	}
	static LongUnaryOperator identity() {
		return t -> t;
	}
	default <E extends Exception> ExLongUnaryOperator<E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t) -> {
			try {
				return applyAsLong(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
