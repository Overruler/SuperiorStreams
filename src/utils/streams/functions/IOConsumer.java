package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface IOConsumer<T> extends ExConsumer<T, IOException> {
	default IOConsumer<T> andThen(IOConsumer<T> after) {
		Objects.requireNonNull(after);
		return (T t) -> {
			accept(t);
			after.accept(t);
		};
	}
	static <T> IOConsumer<T> recheck(java.util.function.Consumer<T> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				unchecked.accept(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.Consumer<T> uncheck() {
		return (T t) -> {
			try {
				accept(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
