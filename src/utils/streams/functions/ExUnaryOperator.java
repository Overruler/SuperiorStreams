package utils.streams.functions;

import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface ExUnaryOperator<T, E extends Exception> extends ExFunction<T, T, E> {
	default ExUnaryOperator<T, E> compose(ExUnaryOperator<T, E> before) {
		Objects.requireNonNull(before);
		return (T t) -> apply(before.apply(t));
	}
	default ExUnaryOperator<T, E> andThen(ExUnaryOperator<T, E> after) {
		Objects.requireNonNull(after);
		return (T t) -> after.apply(apply(t));
	}
	static <T, E extends Exception> ExUnaryOperator<T, E> identity() {
		return t -> t;
	}
	static <T, E extends Exception> ExUnaryOperator<T, E> recheck(
		java.util.function.UnaryOperator<T> unchecked,
		Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				return unchecked.apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, classOfE);
			}
		};
	}
	default @Override java.util.function.UnaryOperator<T> uncheck(Class<E> classOfE) {
		Objects.requireNonNull(classOfE);
		return (T t) -> {
			try {
				return apply(t);
			} catch(Exception e) {
				throw WrapperException.hide(e, classOfE);
			}
		};
	}
}
