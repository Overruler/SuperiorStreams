package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface IODoubleUnaryOperator extends ExDoubleUnaryOperator<IOException> {
	default IODoubleUnaryOperator compose(IODoubleUnaryOperator before) {
		Objects.requireNonNull(before);
		return (double v) -> applyAsDouble(before.applyAsDouble(v));
	}
	default IODoubleUnaryOperator andThen(IODoubleUnaryOperator after) {
		Objects.requireNonNull(after);
		return (double t) -> after.applyAsDouble(applyAsDouble(t));
	}
	static IODoubleUnaryOperator identity() {
		return t -> t;
	}
	static IODoubleUnaryOperator recheck(java.util.function.DoubleUnaryOperator unchecked) {
		Objects.requireNonNull(unchecked);
		return (double t) -> {
			try {
				return unchecked.applyAsDouble(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.DoubleUnaryOperator uncheck() {
		return (double t) -> {
			try {
				return applyAsDouble(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
