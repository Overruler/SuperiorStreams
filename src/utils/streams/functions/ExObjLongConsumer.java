package utils.streams.functions;

import java.util.Objects;
import java.util.function.ObjLongConsumer;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExObjLongConsumer<T, E extends Exception> {

	void accept(T t1, long t2) throws E;

	default ExObjLongConsumer<T, E> andThen(ExObjLongConsumer<T, E> after) {
		Objects.requireNonNull(after);
		return (T t1, long t2) -> {
			accept(t1, t2);
			after.accept(t1, t2);
		};
	}

	static <T, E extends Exception> ExObjLongConsumer<T, E> recheck(ObjLongConsumer<T> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default ObjLongConsumer<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

