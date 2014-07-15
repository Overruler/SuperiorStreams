package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.Consumer
 * @param <T>
 */
@FunctionalInterface
public interface Consumer<T> extends ExConsumer<T, RuntimeException>, java.util.function.Consumer<T> {
	default Consumer<T> andThen(Consumer<T> after) {
		Objects.requireNonNull(after);
		return (T t) -> {
			accept(t);
			after.accept(t);
		};
	}
}
