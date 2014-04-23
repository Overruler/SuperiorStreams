package utils.streams.functions;

import java.util.Objects;
import java.util.function.ObjIntConsumer;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExObjIntConsumer<T, E extends Exception> {

	void accept(T t1, int t2) throws E;

	default ExObjIntConsumer<T, E> andThen(ExObjIntConsumer<T, E> after) {
		Objects.requireNonNull(after);
		return (T t1, int t2) -> {
			accept(t1, t2);
			after.accept(t1, t2);
		};
	}

	static <T, E extends Exception> ExObjIntConsumer<T, E> recheck(ObjIntConsumer<T> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default ObjIntConsumer<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

