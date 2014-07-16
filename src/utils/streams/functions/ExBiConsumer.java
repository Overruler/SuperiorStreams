package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <U>
 * @param <E>
 * @see java.util.function.BiConsumer
 */
@FunctionalInterface
public interface ExBiConsumer<T, U, E extends Exception> {
	/**
	 * @param t
	 * @param u
	 * @throws E
	 * @see java.util.function.BiConsumer#accept
	 */
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
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t, U u) -> {
			try {
				unchecked.accept(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default BiConsumer<T, U> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t, U u) -> {
			try {
				accept(t, u);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
