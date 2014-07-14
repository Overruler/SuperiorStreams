package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExUnaryOperator<T, E extends Exception> {
	T apply(T t1) throws E;
	static <T, E extends Exception> ExUnaryOperator<T, E> recheck(
		java.util.function.UnaryOperator<T> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.UnaryOperator<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
