package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExDoubleFunction<R, E extends Exception> {
	R apply(double t) throws E;
	default <V> ExDoubleFunction<V, E> andThen(ExFunction<? super R, ? extends V, E> after) {
		Objects.requireNonNull(after);
		return (double t) -> after.apply(apply(t));
	}
	static <R, E extends Exception> ExDoubleFunction<R, E> recheck(
		java.util.function.DoubleFunction<R> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.DoubleFunction<R> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
