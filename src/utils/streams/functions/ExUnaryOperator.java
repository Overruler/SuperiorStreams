package utils.streams.functions;

import java.util.function.UnaryOperator;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExUnaryOperator<T, E extends Exception> {

	T apply(T t1) throws E;

	static <T, E extends Exception> ExUnaryOperator<T, E> recheck(UnaryOperator<T> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default UnaryOperator<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

