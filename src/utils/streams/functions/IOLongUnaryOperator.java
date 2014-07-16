package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.LongUnaryOperator
 */
@FunctionalInterface
public interface IOLongUnaryOperator extends ExLongUnaryOperator<IOException> {
	default IOLongUnaryOperator compose(IOLongUnaryOperator before) {
		Objects.requireNonNull(before);
		return (long v) -> applyAsLong(before.applyAsLong(v));
	}
	default IOLongUnaryOperator andThen(IOLongUnaryOperator after) {
		Objects.requireNonNull(after);
		return (long t) -> after.applyAsLong(applyAsLong(t));
	}
	static IOLongUnaryOperator identity() {
		return t -> t;
	}
	static IOLongUnaryOperator recheck(java.util.function.LongUnaryOperator unchecked) {
		Objects.requireNonNull(unchecked);
		return (long t) -> {
			try {
				return unchecked.applyAsLong(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default LongUnaryOperator uncheck() {
		return (long t) -> {
			try {
				return applyAsLong(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
