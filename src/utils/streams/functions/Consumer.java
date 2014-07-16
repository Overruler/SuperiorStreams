package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.Consumer
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
	default <E extends Exception> ExConsumer<T, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				accept(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
