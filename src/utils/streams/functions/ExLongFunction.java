package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <R>
 * @param <E>
 * @see java.util.function.LongFunction
 */
@FunctionalInterface
public interface ExLongFunction<R, E extends Exception> {
	/**
	 * @param t
	 * @return R
	 * @throws E
	 * @see java.util.function.LongFunction#apply
	 */
	R apply(long t) throws E;
	default <V> ExLongFunction<V, E> andThen(ExFunction<? super R, ? extends V, E> after) {
		Objects.requireNonNull(after);
		return (long t) -> after.apply(apply(t));
	}
	static <R, E extends Exception> ExLongFunction<R, E> recheck(
		java.util.function.LongFunction<R> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (long t) -> {
			try {
				return unchecked.apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default LongFunction<R> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t) -> {
			try {
				return apply(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
