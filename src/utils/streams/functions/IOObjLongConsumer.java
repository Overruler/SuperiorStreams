package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.ObjLongConsumer
 */
@FunctionalInterface
public interface IOObjLongConsumer<T> extends ExObjLongConsumer<T, IOException> {
	default IOObjLongConsumer<T> andThen(IOObjLongConsumer<? super T> after) {
		Objects.requireNonNull(after);
		return (T t, long u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
	static <T> IOObjLongConsumer<T> recheck(java.util.function.ObjLongConsumer<T> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t, long u) -> {
			try {
				unchecked.accept(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default ObjLongConsumer<T> uncheck() {
		return (T t, long u) -> {
			try {
				accept(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
