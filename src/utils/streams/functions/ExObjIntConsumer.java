package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <E>
 * @see java.util.function.ObjIntConsumer
 */
@FunctionalInterface
public interface ExObjIntConsumer<T, E extends Exception> {
	/**
	 * @param t
	 * @param u
	 * @throws E
	 * @see java.util.function.ObjIntConsumer#accept
	 */
	void accept(T t, int u) throws E;
	default ExObjIntConsumer<T, E> andThen(ExObjIntConsumer<? super T, E> after) {
		Objects.requireNonNull(after);
		return (T t, int u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
	static <T, E extends Exception> ExObjIntConsumer<T, E> recheck(
		java.util.function.ObjIntConsumer<T> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t, int u) -> {
			try {
				unchecked.accept(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default ObjIntConsumer<T> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, int u) -> {
			try {
				accept(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
