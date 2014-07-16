package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <R>
 * @see java.util.function.IntFunction
 */
@FunctionalInterface
public interface IntFunction<R> extends ExIntFunction<R, RuntimeException>,
	java.util.function.IntFunction<R> {
	default <V> IntFunction<V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (int t) -> after.apply(apply(t));
	}
	default <E extends Exception> ExIntFunction<R, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t) -> {
			try {
				return apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
