package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @param <R>
 * @see java.util.function.BiFunction
 */
@FunctionalInterface
public interface BiFunction<T, U, R> extends ExBiFunction<T, U, R, RuntimeException>,
	java.util.function.BiFunction<T, U, R> {
	default <V> BiFunction<T, U, V> andThen(Function<? super R, ? extends V> after) {
		Objects.requireNonNull(after);
		return (T t, U u) -> after.apply(apply(t, u));
	}
	default <E extends Exception> ExBiFunction<T, U, R, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, U u) -> {
			try {
				return apply(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
