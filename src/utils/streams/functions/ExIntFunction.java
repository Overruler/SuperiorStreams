package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <R>
 * @param <E>
 * @see java.util.function.IntFunction
 */
@FunctionalInterface
public interface ExIntFunction<R, E extends Exception> {
	/**
	 * @param t
	 * @return R
	 * @throws E
	 * @see java.util.function.IntFunction#apply
	 */
	R apply(int t) throws E;
	default <V> ExIntFunction<V, E> andThen(ExFunction<? super R, ? extends V, E> after) {
		Objects.requireNonNull(after);
		return (int t) -> after.apply(apply(t));
	}
	static <R, E extends Exception> ExIntFunction<R, E> recheck(
		java.util.function.IntFunction<R> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (int t) -> {
			try {
				return unchecked.apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default IntFunction<R> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t) -> {
			try {
				return apply(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
