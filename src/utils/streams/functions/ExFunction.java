package utils.streams.functions;

import java.util.Objects;
import java.util.function.Function;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExFunction<T, R, E extends Exception> {

	R apply(T t1) throws E;

	default <V>  ExFunction<V, R, E> compose(ExFunction<? super V, ? extends T, E> before) {
		Objects.requireNonNull(before);
		return (V v) -> apply(before.apply(v));
	}

	default <V>  ExFunction<T, V, E> andThen(ExFunction<? super R, ? extends V, E> after) {
		Objects.requireNonNull(after);
		return (T t) -> after.apply(apply(t));
	}

	static <T, E extends Exception>  ExFunction<T, T, E> identity() {
		return t -> t;
	}

	static <T, R, E extends Exception> ExFunction<T, R, E> recheck(Function<T, R> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default Function<T, R> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

