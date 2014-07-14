package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExToIntFunction<T, E extends Exception> {
	int applyAsInt(T t1) throws E;
	static <T, E extends Exception> ExToIntFunction<T, E> recheck(
		java.util.function.ToIntFunction<T> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.ToIntFunction<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
