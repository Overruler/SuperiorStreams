package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.ObjIntConsumer
 * @param <T>
 */
@FunctionalInterface
public interface ObjIntConsumer<T> extends ExObjIntConsumer<T, RuntimeException>, java.util.function.ObjIntConsumer<T> {
	default ObjIntConsumer<T> andThen(ObjIntConsumer<T> after) {
		Objects.requireNonNull(after);
		return (T t, int u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
}
