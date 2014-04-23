package utils.streams.functions;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoubleUnaryOperator<E extends Exception> {

	double applyAsDouble(double t1) throws E;

	default  ExDoubleUnaryOperator<E> compose(ExDoubleUnaryOperator<E> before) {
		Objects.requireNonNull(before);
		return (double v) -> applyAsDouble(before.applyAsDouble(v));
	}

	default  ExDoubleUnaryOperator<E> andThen(ExDoubleUnaryOperator<E> after) {
		Objects.requireNonNull(after);
		return (double t) -> after.applyAsDouble(applyAsDouble(t));
	}

	static <E extends Exception>  ExDoubleUnaryOperator<E> identity() {
		return t -> t;
	}

	static <E extends Exception> ExDoubleUnaryOperator<E> recheck(DoubleUnaryOperator unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default DoubleUnaryOperator uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

