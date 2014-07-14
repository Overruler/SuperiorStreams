package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExToLongFunction<T, E extends Exception> {
	long applyAsLong(T t1) throws E;
	static <T, E extends Exception> ExToLongFunction<T, E> recheck(
		java.util.function.ToLongFunction<T> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.ToLongFunction<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
