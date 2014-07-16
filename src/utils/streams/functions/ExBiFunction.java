package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @param <R>
 * @param <E>
 * @see java.util.function.BiFunction
 */
@FunctionalInterface
public interface ExBiFunction<T, U, R, E extends Exception> {
	/**
	 * @param t
	 * @param u
	 * @return R
	 * @throws E
	 * @see java.util.function.BiFunction#apply
	 */
	R apply(T t, U u) throws E;
	default <V> ExBiFunction<T, U, V, E> andThen(ExFunction<? super R, ? extends V, E> after) {
		Objects.requireNonNull(after);
		return (T t, U u) -> after.apply(apply(t, u));
	}
	static <T, U, R, E extends Exception> ExBiFunction<T, U, R, E> recheck(
		java.util.function.BiFunction<T, U, R> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t, U u) -> {
			try {
				return unchecked.apply(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default BiFunction<T, U, R> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, U u) -> {
			try {
				return apply(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
