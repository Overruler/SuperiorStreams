package utils.streams.functions;

import java.util.function.ToDoubleBiFunction;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExToDoubleBiFunction<T, U, E extends Exception> {

	double applyAsDouble(T t1, U t2) throws E;

	static <T, U, E extends Exception> ExToDoubleBiFunction<T, U, E> recheck(ToDoubleBiFunction<T, U> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default ToDoubleBiFunction<T, U> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

