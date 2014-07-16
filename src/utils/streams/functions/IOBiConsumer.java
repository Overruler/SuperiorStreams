package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @see java.util.function.BiConsumer
 */
@FunctionalInterface
public interface IOBiConsumer<T, U> extends ExBiConsumer<T, U, IOException> {
	default IOBiConsumer<T, U> andThen(IOBiConsumer<? super T, ? super U> after) {
		Objects.requireNonNull(after);
		return (T t, U u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
	static <T, U> IOBiConsumer<T, U> recheck(java.util.function.BiConsumer<T, U> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t, U u) -> {
			try {
				unchecked.accept(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default BiConsumer<T, U> uncheck() {
		return (T t, U u) -> {
			try {
				accept(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
