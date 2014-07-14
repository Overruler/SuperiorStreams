package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExIntFunction<R, E extends Exception> {
	R apply(int t) throws E;
	default <V> ExIntFunction<V, E> andThen(ExFunction<? super R, ? extends V, E> after) {
		Objects.requireNonNull(after);
		return (int t) -> after.apply(apply(t));
	}
	static <R, E extends Exception> ExIntFunction<R, E> recheck(
		java.util.function.IntFunction<R> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.IntFunction<R> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
