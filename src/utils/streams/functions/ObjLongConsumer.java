package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.ObjLongConsumer
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
	default <E extends Exception> ExObjLongConsumer<T, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, long u) -> {
			try {
				accept(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
