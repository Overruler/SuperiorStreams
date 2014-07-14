package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoubleToLongFunction<E extends Exception> {
	long applyAsLong(double t1) throws E;
	static <E extends Exception> ExDoubleToLongFunction<E> recheck(
		java.util.function.DoubleToLongFunction unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.DoubleToLongFunction uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
