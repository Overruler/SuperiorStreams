package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleBinaryOperator

 */
@FunctionalInterface
public interface IODoubleBinaryOperator extends ExDoubleBinaryOperator<IOException> {
	static IODoubleBinaryOperator recheck(java.util.function.DoubleBinaryOperator unchecked) {
		Objects.requireNonNull(unchecked);
		return (double t, double u) -> {
			try {
				return unchecked.applyAsDouble(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.DoubleBinaryOperator uncheck() {
		return (double t, double u) -> {
			try {
				return applyAsDouble(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
