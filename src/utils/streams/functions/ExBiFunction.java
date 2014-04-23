package utils.streams.functions;

import java.util.Objects;
import java.util.function.BiFunction;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExBiFunction<T, U, R, E extends Exception> {

	R apply(T t1, U t2) throws E;

	default <V> ExBiFunction<T, U, V, E> andThen(ExFunction<? super R, ? extends V, E> after) {
		Objects.requireNonNull(after);
		return (T t, U u) -> after.apply(apply(t, u));
	}

	static <T, U, R, E extends Exception> ExBiFunction<T, U, R, E> recheck(BiFunction<T, U, R> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default BiFunction<T, U, R> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

