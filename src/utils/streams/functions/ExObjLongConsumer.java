package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <E>
 * @see java.util.function.ObjLongConsumer
 */
@FunctionalInterface
public interface ExObjLongConsumer<T, E extends Exception> {
	/**
	 * @param t
	 * @param u
	 * @throws E
	 * @see java.util.function.ObjLongConsumer#accept
	 */
	void accept(T t, long u) throws E;
	default ExObjLongConsumer<T, E> andThen(ExObjLongConsumer<? super T, E> after) {
		Objects.requireNonNull(after);
		return (T t, long u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
	static <T, E extends Exception> ExObjLongConsumer<T, E> recheck(
		java.util.function.ObjLongConsumer<T> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t, long u) -> {
			try {
				unchecked.accept(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default ObjLongConsumer<T> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, long u) -> {
			try {
				accept(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
