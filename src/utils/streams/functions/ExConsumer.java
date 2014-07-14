package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExConsumer<T, E extends Exception> {
	void accept(T t) throws E;
	default ExConsumer<T, E> andThen(ExConsumer<T, E> after) {
		Objects.requireNonNull(after);
		return (T t) -> {
			accept(t);
			after.accept(t);
		};
	}
	static <T, E extends Exception> ExConsumer<T, E> recheck(
		java.util.function.Consumer<T> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.Consumer<T> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
