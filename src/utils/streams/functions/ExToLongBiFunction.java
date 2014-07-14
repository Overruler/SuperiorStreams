package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExToLongBiFunction<T, U, E extends Exception> {
	long applyAsLong(T t1, U t2) throws E;
	static <T, U, E extends Exception> ExToLongBiFunction<T, U, E> recheck(
		java.util.function.ToLongBiFunction<T, U> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.ToLongBiFunction<T, U> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
