package utils.streams.functions;

import java.util.Objects;
import java.util.function.Consumer;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExConsumer<T, E extends Exception> {

	void accept(T t1) throws E;

	default ExConsumer<T, E> andThen(ExConsumer<T, E> after) {
		Objects.requireNonNull(after);
		return (T t1) -> {
			accept(t1);
			after.accept(t1);
		};
	}

	static <T, E extends Exception> ExConsumer<T, E> recheck(Consumer<T> unchecked, Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}

	default Consumer<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}

