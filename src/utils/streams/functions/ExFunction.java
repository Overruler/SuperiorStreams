package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <R>
 * @param <E>
 * @see java.util.function.Function
 */
@FunctionalInterface
public interface ExFunction<T, R, E extends Exception> {
	/**
	 * @param t
	 * @return R
	 * @throws E
	 * @see java.util.function.Function#apply
	 */
	R apply(T t) throws E;
	default <V> ExFunction<V, R, E> compose(ExFunction<? super V, ? extends T, E> before) {
		Objects.requireNonNull(before);
		return (V v) -> apply(before.apply(v));
	}
	default <V> ExFunction<T, V, E> andThen(ExFunction<? super R, ? extends V, E> after) {
		Objects.requireNonNull(after);
		return (T t) -> after.apply(apply(t));
	}
	static <T, E extends Exception> ExFunction<T, T, E> identity() {
		return t -> t;
	}
	static <T, R, E extends Exception> ExFunction<T, R, E> recheck(
		java.util.function.Function<T, R> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				return unchecked.apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default Function<T, R> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return apply(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
