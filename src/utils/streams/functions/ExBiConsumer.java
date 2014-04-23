package utils.streams.functions;

import java.util.Objects;
import java.util.function.BiConsumer;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExBiConsumer<T, U, E extends Exception> {

	void accept(T t1, U t2) throws E;

	default ExBiConsumer<T, U, E> andThen(ExBiConsumer<T, U, E> after) {
		Objects.requireNonNull(after);
		return (T t1, U t2) -> {
			accept(t1, t2);
			after.accept(t1, t2);
		};
	}

	static <T, U, E extends Exception> ExBiConsumer<T, U, E> recheck(BiConsumer<T, U> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default BiConsumer<T, U> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

