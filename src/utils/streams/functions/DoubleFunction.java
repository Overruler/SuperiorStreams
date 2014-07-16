package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <R>
 * @see java.util.function.DoubleFunction
 */
@FunctionalInterface
public interface DoubleFunction<R> extends ExDoubleFunction<R, RuntimeException>,
	java.util.function.DoubleFunction<R> {
	default <V> DoubleFunction<V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (double t) -> after.apply(apply(t));
	}
	default <E extends Exception> ExDoubleFunction<R, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t) -> {
			try {
				return apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
