package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.ObjIntConsumer
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
	default <E extends Exception> ExObjIntConsumer<T, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, int u) -> {
			try {
				accept(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
