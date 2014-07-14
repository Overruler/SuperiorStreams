package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface IOObjDoubleConsumer<T> extends ExObjDoubleConsumer<T, IOException> {
	default IOObjDoubleConsumer<T> andThen(IOObjDoubleConsumer<T> after) {
		Objects.requireNonNull(after);
		return (T t, double u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
	static <T> IOObjDoubleConsumer<T> recheck(java.util.function.ObjDoubleConsumer<T> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t, double u) -> {
			try {
				unchecked.accept(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.ObjDoubleConsumer<T> uncheck() {
		return (T t, double u) -> {
			try {
				accept(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
