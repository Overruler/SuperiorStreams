package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @param <T>
 * @see java.util.function.UnaryOperator
 */
@FunctionalInterface
public interface UnaryOperator<T> extends ExUnaryOperator<T, RuntimeException>, Function<T, T>,
	java.util.function.UnaryOperator<T> {
	default UnaryOperator<T> compose(UnaryOperator<T> before) {
		Objects.requireNonNull(before);
		return (T t) -> apply(before.apply(t));
	}
	default UnaryOperator<T> andThen(UnaryOperator<T> after) {
		Objects.requireNonNull(after);
		return (T t) -> after.apply(apply(t));
	}
	static <T> UnaryOperator<T> identity() {
		return t -> t;
	}
	default @Override <E extends Exception> ExUnaryOperator<T, E> recheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
}
