package utils.streams.functions;

import java.util.Objects;

/**
 * @see java.util.function.ObjDoubleConsumer
 * @param <T>
 */
@FunctionalInterface
public interface ObjDoubleConsumer<T> extends ExObjDoubleConsumer<T, RuntimeException>, java.util.function.ObjDoubleConsumer<T> {
	default ObjDoubleConsumer<T> andThen(ObjDoubleConsumer<T> after) {
		Objects.requireNonNull(after);
		return (T t, double u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
}
