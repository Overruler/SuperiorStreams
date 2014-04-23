package utils.streams.functions;

import java.util.Objects;
import java.util.function.ObjDoubleConsumer;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExObjDoubleConsumer<T, E extends Exception> {

	void accept(T t1, double t2) throws E;

	default ExObjDoubleConsumer<T, E> andThen(ExObjDoubleConsumer<T, E> after) {
		Objects.requireNonNull(after);
		return (T t1, double t2) -> {
			accept(t1, t2);
			after.accept(t1, t2);
		};
	}

	static <T, E extends Exception> ExObjDoubleConsumer<T, E> recheck(ObjDoubleConsumer<T> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default ObjDoubleConsumer<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

