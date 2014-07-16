package utils.streams.functions;

import java.io.IOException;
import java.util.Objects;
import utils.streams.WrapperException;

/**
 * @see java.util.function.DoubleToLongFunction
 */
@FunctionalInterface
public interface IODoubleToLongFunction extends ExDoubleToLongFunction<IOException> {
	static IODoubleToLongFunction recheck(java.util.function.DoubleToLongFunction unchecked) {
		Objects.requireNonNull(unchecked);
		return (double t) -> {
			try {
				return unchecked.applyAsLong(t);
			} catch(RuntimeException e) {
				throw WrapperException.show(e, IOException.class);
			}
		};
	}
	default DoubleToLongFunction uncheck() {
		return (double t) -> {
			try {
				return applyAsLong(t);
			} catch(IOException e) {
				throw WrapperException.hide(e, IOException.class);
			}
		};
	}
}
