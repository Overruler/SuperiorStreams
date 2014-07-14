package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExObjLongConsumer<T, E extends Exception> {
	void accept(T t, long u) throws E;
	default ExObjLongConsumer<T, E> andThen(ExObjLongConsumer<T, E> after) {
		Objects.requireNonNull(after);
		return (T t, long u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
	static <T, E extends Exception> ExObjLongConsumer<T, E> recheck(
		java.util.function.ObjLongConsumer<T> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.ObjLongConsumer<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
