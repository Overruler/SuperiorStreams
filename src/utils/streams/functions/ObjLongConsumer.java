package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.ObjLongConsumer
 * @param <T>
 */
@FunctionalInterface
public interface ObjLongConsumer<T> extends ExObjLongConsumer<T, RuntimeException>, java.util.function.ObjLongConsumer<T> {
	default ObjLongConsumer<T> andThen(ObjLongConsumer<T> after) {
		Objects.requireNonNull(after);
		return (T t, long u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
}
