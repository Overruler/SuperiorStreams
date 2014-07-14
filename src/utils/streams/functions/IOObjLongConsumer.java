package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface IOObjLongConsumer<T> extends ExObjLongConsumer<T, IOException> {
	default IOObjLongConsumer<T> andThen(IOObjLongConsumer<T> after) {
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
	default java.util.function.ObjLongConsumer<T> uncheck() {
		return (T t, long u) -> {
			try {
				accept(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
