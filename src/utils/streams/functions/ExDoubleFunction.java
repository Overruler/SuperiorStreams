package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleFunction
 * @param <R>
 * @param <E>
 */
@FunctionalInterface
public interface ExDoubleFunction<R, E extends Exception> {
	/**
	 * @param t
	 * @return R
	 * @throws E
	 * @see java.util.function.DoubleFunction#apply
	 */
	R apply(double t) throws E;
	default <V> ExDoubleFunction<V, E> andThen(ExFunction<? super R, ? extends V, E> after) {
		Objects.requireNonNull(after);
		return (double t) -> after.apply(apply(t));
	}
	static <R, E extends Exception> ExDoubleFunction<R, E> recheck(
		java.util.function.DoubleFunction<R> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (double t) -> {
			try {
				return unchecked.apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.DoubleFunction<R> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t) -> {
			try {
				return apply(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
