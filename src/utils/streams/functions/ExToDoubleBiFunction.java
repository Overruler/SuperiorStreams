package utils.streams.functions;

import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExToDoubleBiFunction<T, U, E extends Exception> {
	double applyAsDouble(T t1, U t2) throws E;
	static <T, U, E extends Exception> ExToDoubleBiFunction<T, U, E> recheck(
		java.util.function.ToDoubleBiFunction<T, U> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.ToDoubleBiFunction<T, U> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
