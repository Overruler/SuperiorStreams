package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExLongToDoubleFunction<E extends Exception> {
	double applyAsDouble(long t1) throws E;
	static <E extends Exception> ExLongToDoubleFunction<E> recheck(
		java.util.function.LongToDoubleFunction unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.LongToDoubleFunction uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
