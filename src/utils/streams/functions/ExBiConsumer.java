package utils.streams.functions;

import java.util.Objects;
import static utils.streams.functions.Conversions.*;

@FunctionalInterface
public interface ExBiConsumer<T, U, E extends Exception> {
	void accept(T t, U u) throws E;
	default ExBiConsumer<T, U, E> andThen(ExBiConsumer<T, U, E> after) {
		Objects.requireNonNull(after);
		return (T t, U u) -> {
			accept(t, u);
			after.accept(t, u);
		};
	}
	static <T, U, E extends Exception> ExBiConsumer<T, U, E> recheck(
		java.util.function.BiConsumer<T, U> unchecked,
		Class<E> classOfE) {
		return rechecked(classOfE, unchecked);
	}
	default java.util.function.BiConsumer<T, U> uncheck(Class<E> classOfE) {
		return unchecked(classOfE, this);
	}
}
