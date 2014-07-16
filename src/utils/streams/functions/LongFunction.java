package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <R>
 * @see java.util.function.LongFunction
 */
@FunctionalInterface
public interface LongFunction<R> extends ExLongFunction<R, RuntimeException>,
	java.util.function.LongFunction<R> {
	default <V> LongFunction<V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (long t) -> after.apply(apply(t));
	}
	default <E extends Exception> ExLongFunction<R, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t) -> {
			try {
				return apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
