package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExToIntBiFunction<T, U, E extends Exception> {
	int applyAsInt(T t1, U t2) throws E;
	static <T, U, E extends Exception> ExToIntBiFunction<T, U, E> recheck(
		java.util.function.ToIntBiFunction<T, U> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.ToIntBiFunction<T, U> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
