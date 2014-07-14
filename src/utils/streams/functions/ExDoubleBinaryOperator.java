package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoubleBinaryOperator<E extends Exception> {
	double applyAsDouble(double t1, double t2) throws E;
	static <E extends Exception> ExDoubleBinaryOperator<E> recheck(
		java.util.function.DoubleBinaryOperator unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.DoubleBinaryOperator uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
