package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExBiFunction<T, U, R, E extends Exception> {
	R apply(T t, U u) throws E;
	default <V> ExBiFunction<T, U, V, E> andThen(ExFunction<? super R, ? extends V, E> after) {
		Objects.requireNonNull(after);
		return (T t, U u) -> after.apply(apply(t, u));
	}
	static <T, U, R, E extends Exception> ExBiFunction<T, U, R, E> recheck(
		java.util.function.BiFunction<T, U, R> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.BiFunction<T, U, R> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
