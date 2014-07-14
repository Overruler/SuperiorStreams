package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

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
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.ObjIntConsumer<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
