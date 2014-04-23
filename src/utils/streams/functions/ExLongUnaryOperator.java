package utils.streams.functions;

import java.util.Objects;
import java.util.function.LongUnaryOperator;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongUnaryOperator<E extends Exception> {

	long applyAsLong(long t1) throws E;

	default  ExLongUnaryOperator<E> compose(ExLongUnaryOperator<E> before) {
		Objects.requireNonNull(before);
		return (long v) -> applyAsLong(before.applyAsLong(v));
	}

	default  ExLongUnaryOperator<E> andThen(ExLongUnaryOperator<E> after) {
		Objects.requireNonNull(after);
		return (long t) -> after.applyAsLong(applyAsLong(t));
	}

	static <E extends Exception>  ExLongUnaryOperator<E> identity() {
		return t -> t;
	}

	static <E extends Exception> ExLongUnaryOperator<E> recheck(LongUnaryOperator unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default LongUnaryOperator uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

