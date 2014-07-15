package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.IntUnaryOperator

 */
@FunctionalInterface
public interface IOIntUnaryOperator extends ExIntUnaryOperator<IOException> {
	default IOIntUnaryOperator compose(IOIntUnaryOperator before) {
		Objects.requireNonNull(before);
		return (int v) -> applyAsInt(before.applyAsInt(v));
	}
	default IOIntUnaryOperator andThen(IOIntUnaryOperator after) {
		Objects.requireNonNull(after);
		return (int t) -> after.applyAsInt(applyAsInt(t));
	}
	static IOIntUnaryOperator identity() {
		return t -> t;
	}
	static IOIntUnaryOperator recheck(java.util.function.IntUnaryOperator unchecked) {
		Objects.requireNonNull(unchecked);
		return (int t) -> {
			try {
				return unchecked.applyAsInt(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.IntUnaryOperator uncheck() {
		return (int t) -> {
			try {
				return applyAsInt(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
