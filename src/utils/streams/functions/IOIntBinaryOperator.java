package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

@FunctionalInterface
public interface IOIntBinaryOperator extends ExIntBinaryOperator<IOException> {
	static IOIntBinaryOperator recheck(java.util.function.IntBinaryOperator unchecked) {
		Objects.requireNonNull(unchecked);
		return (int t, int u) -> {
			try {
				return unchecked.applyAsInt(t, u);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default java.util.function.IntBinaryOperator uncheck() {
		return (int t, int u) -> {
			try {
				return applyAsInt(t, u);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
