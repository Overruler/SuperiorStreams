package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.UnaryOperator
 * @param <T>
 */
@FunctionalInterface
public interface IOUnaryOperator<T> extends ExUnaryOperator<T, IOException>, IOFunction<T, T> {
	default IOUnaryOperator<T> compose(IOUnaryOperator<T> before) {
		Objects.requireNonNull(before);
		return (T t) -> apply(before.apply(t));
	}
	default IOUnaryOperator<T> andThen(IOUnaryOperator<T> after) {
		Objects.requireNonNull(after);
		return (T t) -> after.apply(apply(t));
	}
	static <T> IOUnaryOperator<T> identity() {
		return t -> t;
	}
	static <T> IOUnaryOperator<T> recheck(java.util.function.UnaryOperator<T> unchecked) {
		Objects.requireNonNull(unchecked);
		return (T t) -> {
			try {
				return unchecked.apply(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default @Override java.util.function.UnaryOperator<T> uncheck() {
		return (T t) -> {
			try {
				return apply(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
