package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @see java.util.function.BiConsumer
 */
@FunctionalInterface
public interface BiConsumer<T, U> extends ExBiConsumer<T, U, RuntimeException>, java.util.function.BiConsumer<T, U> {
	default BiConsumer<T, U> andThen(BiConsumer<T, U> after) {
		Objects.requireNonNull(after);
		return (T t, U u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
	default <E extends Exception> ExBiConsumer<T, U, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, U u) -> {
			try {
				accept(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
