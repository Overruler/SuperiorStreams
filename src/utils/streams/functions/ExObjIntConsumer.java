package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface ExObjIntConsumer<T, E extends Exception> {
	void accept(T t, int u) throws E;
	default ExObjIntConsumer<T, E> andThen(ExObjIntConsumer<T, E> after) {
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
	default java.util.function.ObjIntConsumer<T> uncheck(Class<E> classOfE) {
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
