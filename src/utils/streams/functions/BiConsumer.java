package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.BiConsumer
 * @param <T>
 * @param <U>
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
}
