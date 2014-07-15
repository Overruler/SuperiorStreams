package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.ObjDoubleConsumer
 * @param <T>
 * @param <E>
 */
@FunctionalInterface
public interface ExObjDoubleConsumer<T, E extends Exception> {
	/**
	 * @param t
	 * @param u
	 * @throws E
	 * @see java.util.function.ObjDoubleConsumer#accept
	 */
	void accept(T t, double u) throws E;
	default ExObjDoubleConsumer<T, E> andThen(ExObjDoubleConsumer<T, E> after) {
		Objects.requireNonNull(after);
		return (T t, double u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
	static <T, E extends Exception> ExObjDoubleConsumer<T, E> recheck(
		java.util.function.ObjDoubleConsumer<T> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t, double u) -> {
			try {
				unchecked.accept(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.ObjDoubleConsumer<T> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, double u) -> {
			try {
				accept(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
