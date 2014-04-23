package utils.streams.functions;

import java.util.function.ToDoubleFunction;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExToDoubleFunction<T, E extends Exception> {

	double applyAsDouble(T t1) throws E;

	static <T, E extends Exception> ExToDoubleFunction<T, E> recheck(ToDoubleFunction<T> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default ToDoubleFunction<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

