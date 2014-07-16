package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <E>
 * @see java.util.function.IntConsumer
 */
@FunctionalInterface
public interface ExIntConsumer<E extends Exception> {
	/**
	 * @param t
	 * @throws E
	 * @see java.util.function.IntConsumer#accept
	 */
	void accept(int t) throws E;
	default ExIntConsumer<E> andThen(ExIntConsumer<E> after) {
		Objects.requireNonNull(after);
		return (int t) -> {
			accept(t);
			after.accept(t);
		};
	}
	static <E extends Exception> ExIntConsumer<E> recheck(
		java.util.function.IntConsumer unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (int t) -> {
			try {
				unchecked.accept(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default IntConsumer uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (int t) -> {
			try {
				accept(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
