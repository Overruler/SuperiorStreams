package utils.streams.functions;

import java.util.function.ToIntBiFunction;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExToIntBiFunction<T, U, E extends Exception> {

	int applyAsInt(T t1, U t2) throws E;

	static <T, U, E extends Exception> ExToIntBiFunction<T, U, E> recheck(ToIntBiFunction<T, U> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default ToIntBiFunction<T, U> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

