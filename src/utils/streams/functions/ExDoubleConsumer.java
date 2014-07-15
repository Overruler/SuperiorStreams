package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleConsumer
 * @param <E>
 */
@FunctionalInterface
public interface ExDoubleConsumer<E extends Exception> {
	/**
	 * @param t
	 * @throws E
	 * @see java.util.function.DoubleConsumer#accept
	 */
	void accept(double t) throws E;
	default ExDoubleConsumer<E> andThen(ExDoubleConsumer<E> after) {
		Objects.requireNonNull(after);
		return (double t) -> {
			accept(t);
			after.accept(t);
		};
	}
	static <E extends Exception> ExDoubleConsumer<E> recheck(
		java.util.function.DoubleConsumer unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (double t) -> {
			try {
				unchecked.accept(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default java.util.function.DoubleConsumer uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (double t) -> {
			try {
				accept(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
