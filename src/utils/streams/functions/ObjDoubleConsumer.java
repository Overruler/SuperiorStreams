package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.ObjDoubleConsumer
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
	default <E extends Exception> ExObjDoubleConsumer<T, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, double u) -> {
			try {
				accept(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
