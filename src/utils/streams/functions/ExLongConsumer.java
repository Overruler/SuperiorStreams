package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongConsumer
 * @param <E>
 */
@FunctionalInterface
public interface ExLongConsumer<E extends Exception> {
	/**
	 * @param t
	 * @throws E
	 * @see java.util.function.LongConsumer#accept
	 */
	void accept(long t) throws E;
	default ExLongConsumer<E> andThen(ExLongConsumer<E> after) {
		Objects.requireNonNull(after);
		return (long t) -> {
			accept(t);
			after.accept(t);
		};
	}
	static <E extends Exception> ExLongConsumer<E> recheck(
		java.util.function.LongConsumer unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (long t) -> {
			try {
				unchecked.accept(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.LongConsumer uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (long t) -> {
			try {
				accept(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
