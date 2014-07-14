package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExObjDoubleConsumer<T, E extends Exception> {
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
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.ObjDoubleConsumer<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
