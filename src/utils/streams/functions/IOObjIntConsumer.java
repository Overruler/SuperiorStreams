package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.ObjIntConsumer
 */
@FunctionalInterface
public interface IOObjIntConsumer<T> extends ExObjIntConsumer<T, IOException> {
	default IOObjIntConsumer<T> andThen(IOObjIntConsumer<T> after) {
		Objects.requireNonNull(after);
		return (T t, int u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
	static <T> IOObjIntConsumer<T> recheck(java.util.function.ObjIntConsumer<T> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t, int u) -> {
			try {
				unchecked.accept(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default ObjIntConsumer<T> uncheck() {
		return (T t, int u) -> {
			try {
				accept(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
