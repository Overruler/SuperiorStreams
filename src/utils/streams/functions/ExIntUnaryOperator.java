package utils.streams.functions;

import java.util.Objects;
import java.util.function.IntUnaryOperator;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExIntUnaryOperator<E extends Exception> {

	int applyAsInt(int t1) throws E;

	default  ExIntUnaryOperator<E> compose(ExIntUnaryOperator<E> before) {
		Objects.requireNonNull(before);
		return (int v) -> applyAsInt(before.applyAsInt(v));
	}

	default  ExIntUnaryOperator<E> andThen(ExIntUnaryOperator<E> after) {
		Objects.requireNonNull(after);
		return (int t) -> after.applyAsInt(applyAsInt(t));
	}

	static <E extends Exception>  ExIntUnaryOperator<E> identity() {
		return t -> t;
	}

	static <E extends Exception> ExIntUnaryOperator<E> recheck(IntUnaryOperator unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default IntUnaryOperator uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

