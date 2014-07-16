package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @param <E>
 * @see java.util.function.Consumer
 */
@FunctionalInterface
public interface ExConsumer<T, E extends Exception> {
	/**
	 * @param t
	 * @throws E
	 * @see java.util.function.Consumer#accept
	 */
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
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				unchecked.accept(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default Consumer<T> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				accept(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
