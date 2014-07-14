package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface IOLongBinaryOperator extends ExLongBinaryOperator<IOException> {
	static IOLongBinaryOperator recheck(java.util.function.LongBinaryOperator unchecked) {
		Objects.requireNonNull(unchecked);
		return (long t, long u) -> {
			try {
				return unchecked.applyAsLong(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.LongBinaryOperator uncheck() {
		return (long t, long u) -> {
			try {
				return applyAsLong(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
