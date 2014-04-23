package utils.streams.functions;

import java.util.function.ToLongFunction;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExToLongFunction<T, E extends Exception> {

	long applyAsLong(T t1) throws E;

	static <T, E extends Exception> ExToLongFunction<T, E> recheck(ToLongFunction<T> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default ToLongFunction<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

